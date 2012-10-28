package org.hooogle.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Stack;

/**
 * 需要支持
 * 1. ${abc},
 * 2. ${abc.def}
 * 3. ${abc.de${fgh}j}
 * <p/>
 * 1. 每次遇到$就生成一个Var
 * 2. 如果一个Var关闭，则解析Var的值输出
 * 3. 如果一个Var的name长度超过100，则Var解除
 * 4. 如果一个Var的name里包含非$a-zA-Z.{}，则Var解除
 */
public class TemplateDecorator extends OutputStream {
    Stack<Var> stack = new Stack<Var>();
    private OutputStream out;
    private Resolver resolver;
    private long diff;

    public TemplateDecorator(OutputStream out, Map data) {
        this.out = out;
        this.resolver = new MapResolver(data);
    }

    @Override
    public void flush() throws IOException {
        out.flush();
        super.flush();
    }

    @Override
    public void close() throws IOException {
        out.close();
        super.close();
    }

    @Override
    public void write(int b) throws IOException {
        if (b == '$') {
            stack.push(new Var(b));
            diff -= 1; //少写一个字节
        } else if (b == '{') {
            Var var = popNullIfEmpty();
            if (var == null) {
                this.out.write(b);
            } else if (Var.Status.dollar == var.getStatus()) {
                var.setStatus(Var.Status.dollarOpenBrace);
                var.write(b);
                stack.push(var);
                diff -= 1; //少写一个字节'b'
            } else {
                byte[] normal = var.normal();
                this.out.write(normal);
                diff -= 1; //少写一个字节'b'
                diff += normal.length; //多写normal个字节
            }
        } else if (('a' <= b && 'z' >= b) || ('A' <= b && 'Z' >= b) || ('0' <= b && '9' >= b) || '.' == b) {
            Var var = popNullIfEmpty();
            if (var == null) {
                this.out.write(b);
            } else if (Var.Status.dollarOpenBrace == var.getStatus() || Var.Status.dollarOpenBraceName == var.getStatus()) {
                var.setStatus(Var.Status.dollarOpenBraceName);
                var.write(b);
                stack.push(var);
                diff -= 1; //少写一个字节'b'
            } else {
                byte[] normal = var.normal();
                this.out.write(normal);
                this.out.write(b);
                diff += normal.length; //多写normal个字节
            }
        } else if (b == '}') {
            Var var = popNullIfEmpty();
            if (var == null) {
                this.out.write(b);
            } else if (Var.Status.dollarOpenBraceName == var.getStatus()) {
                var.setStatus(Var.Status.dollarOpenBraceNameCloseBrace);
                String name = var.getName();
                String value = evaluate(name);
                byte[] valueBytes = value == null ? ("${" + name + "}").getBytes() : value.getBytes();
                this.write(valueBytes);
                diff -= 1; //少写一个字节'b'
                diff += valueBytes.length; //多写valueBytes个字节
            } else {
                byte[] normal = var.normal();
                this.out.write(normal);
                this.out.write(b);
//                diff -= 1; //少写一个字节'b'
                diff += normal.length; //多写normal个字节
            }
        } else {
            Var var = popNullIfEmpty();
            if (var != null) {
                byte[] normal = var.normal();
                this.out.write(normal);
                diff += normal.length; //多写normal个字节
            }
            this.out.write(b);
        }
    }

    public long getDiff() {
        return diff;
    }

    public void setDiff(long diff) {
        this.diff = diff;
    }

    private String evaluate(String name) {
        return (String) resolver.resolve(name);
    }

    private Var popNullIfEmpty() {
        return stack.empty() ? null : stack.pop();
    }

    private static class Var {
        Status status;
        private ByteArrayOutputStream normal;

        public Var(int b) {
            status = Status.dollar;
            normal = new ByteArrayOutputStream();
            normal.write(b);
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public byte[] normal() {
            return normal.toByteArray();
        }

        public void write(int b) {
            normal.write(b);
        }

        public String getName() {
            return normal.toString().substring(2);
        }

        private static enum Status {dollar, dollarOpenBrace, dollarOpenBraceNameCloseBrace, dollarOpenBraceName}
    }

    private static interface Resolver {
        /**
         * 返回值要么是一个Resolver，要么是字符串，要么是null
         *
         * @param key
         * @return
         */
        Object resolve(String key);
    }

    private static class MapResolver implements Resolver {
        private Map map;

        private MapResolver(Map map) {
            this.map = map;
        }

        @Override
        public Object resolve(String key) {
            if (key == null) return null;
            int index = key.indexOf(".");
            if (index > 0) {
                Object value = map.get(key.substring(0, index));
                value = getResolver(value);
                if (value instanceof Resolver) {
                    value = Resolver.class.cast(value).resolve(key.substring(index + 1));
                    if (value != null) {
                        return value;
                    }
                }
                value = map.get(key);
                return value;
            } else {
                Object value = map.get(key);
                return value instanceof String ? value : null;
            }
        }
    }
    private static Object getResolver(Object value) {
        if (value instanceof Map) {
            value = new MapResolver((Map) value);
        }
        return value;
    }
}

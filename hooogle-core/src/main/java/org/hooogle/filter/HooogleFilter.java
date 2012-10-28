package org.hooogle.filter;

import org.hooogle.util.TemplateDecorator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: shaoaq
 * Date: 12-9-29
 * Time: 下午1:24
 * To change this template use File | Settings | File Templates.
 */
public class HooogleFilter implements Filter {
    //    private Filter[] filters = new Filter[]{new JavascriptFilter()};
//    private Filter[] filters = new Filter[]{new TemplateFilter()};

    //    private Filter[] filters = new Filter[]{};
//    private Filter[] filters = new Filter[]{new JavascriptFilter(),new TemplateFilter()};
    private Filter[] filters = new Filter[]{new TemplateFilter(), new JavascriptFilter()};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        for (Filter filter : filters) {
            filter.init(filterConfig);
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        new HooogleFilterChain(filterChain, filters).doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        for (Filter filter : filters) {
            filter.destroy();
        }
    }

    private static class HooogleFilterChain implements FilterChain {
        private final Filter[] filters;
        private final FilterChain realFilterChain;
        private int index = 0;

        private HooogleFilterChain(FilterChain realFilterChain, Filter... filters) {
            this.filters = filters;
            this.realFilterChain = realFilterChain;
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
            if (index < filters.length) {
                Filter filter = filters[index++];
                filter.doFilter(servletRequest, servletResponse, this);
            } else {
                realFilterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    private static class Patterns {
        private final Pattern[] patterns;

        private Patterns(String... patternStrings) {
            patterns = new Pattern[patternStrings.length];
            for (int i = 0; i < patternStrings.length; i++) {
                patterns[i] = Pattern.compile(patternStrings[i]);
            }
        }

        public boolean matches(ServletRequest servletRequest) {
            if (servletRequest instanceof HttpServletRequest) {
                for (Pattern pattern : patterns) {
                    if (pattern.matcher(((HttpServletRequest) servletRequest).getServletPath()).matches()) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private static class JavascriptFilter implements Filter {
        private Patterns patterns = new Patterns(".*jquery-[\\d\\.]+(min)?\\.js",
                ".*jquery-ui-[\\d\\.]+(\\.custom)?(\\.min)?\\.js",
                ".*jquery.ztree.all-[\\d\\.]+(min)?\\.js");

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            if (patterns.matches(servletRequest)) {
                Writer writer = servletResponse.getWriter();
                writer.append("define(function(require, exports, module) {\n");
                writer.flush();
                filterChain.doFilter(servletRequest, servletResponse);
                writer.append("\n");
                writer.append("module.exports = $\n");
                writer.append("})\n");
                writer.flush();
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

        @Override
        public void destroy() {
        }
    }


    private class TemplateFilter implements Filter {
        private Patterns patterns = new Patterns(".*\\.js", ".*\\.html");
        private Map data = new HashMap();

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            Properties properties = new Properties();
            try {
                properties.load(getClass().getResourceAsStream("/org/hooogle/base.properties"));
                data.putAll(properties);
            } catch (IOException e) {
                throw new ServletException(e);
            }
        }

        @Override
        public void doFilter(ServletRequest servletRequest, final ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            if (patterns.matches(servletRequest)) {
                TemplateDecoratorHttpServletResponseWrapper httpServletResponseWrapper = new TemplateDecoratorHttpServletResponseWrapper((HttpServletResponse) servletResponse, data);
                filterChain.doFilter(servletRequest, httpServletResponseWrapper);
                if (httpServletResponseWrapper.length > 0) {
                    long contentLength = httpServletResponseWrapper.length + httpServletResponseWrapper.getDiff();
                    if (contentLength <= Integer.MAX_VALUE) {
                        servletResponse.setContentLength((int) contentLength);
                    }
                    ((HttpServletResponse) servletResponse).setHeader("content-length", "" + contentLength);
                    ((HttpServletResponse) servletResponse).setHeader("Content-Length", "" + contentLength);

                }
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

        @Override
        public void destroy() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

    }

    private static class TemplateDecoratorHttpServletResponseWrapper extends HttpServletResponseWrapper {
        private HttpServletResponse servletResponse;
        private TemplateDecorator wrapper;
        private long length;

        private ServletOutputStream outputStream = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                wrapper.write(b);
            }

            @Override
            public void flush() throws IOException {
                wrapper.flush();
            }

            @Override
            public void close() throws IOException {
                wrapper.close();
            }
        };
        private PrintWriter printWriter = new PrintWriter(outputStream, true);

        public TemplateDecoratorHttpServletResponseWrapper(HttpServletResponse servletResponse, Map data) throws IOException {
            super(servletResponse);
            this.servletResponse = servletResponse;
            wrapper = new TemplateDecorator(servletResponse.getOutputStream(), data);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return outputStream;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return printWriter;
        }

        @Override
        public void setContentLength(int len) {
            length = len;
//            super.setContentLength(len);
        }

        @Override
        public void setHeader(String name, String value) {
            if (name.equalsIgnoreCase("content-length")) {
                length = Long.valueOf(value);
            }else {
                super.setHeader(name, value);
            }
        }

        public long getDiff() {
            return wrapper.getDiff();
        }
    }
}


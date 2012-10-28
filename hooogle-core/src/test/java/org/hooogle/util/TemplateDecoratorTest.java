package org.hooogle.util;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * TemplateDecorator Tester.
 *
 * @version 1.0
 */
public class TemplateDecoratorTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    @Test
    public void testEvaluateTemplate() throws Exception {
        Map data = new HashMap();
        data.put("abc", "123");
        assertEquals("test var 123", evaluate("test var ${abc}", data));

        data = new HashMap();
        data.put("abc", "啊");
        assertEquals("test var 啊", evaluate("test var ${abc}", data));

        data = new HashMap();
        HashMap abc = new HashMap();
        data.put("abc", abc);
        abc.put("def","456");
        assertEquals("test var 456", evaluate("test var ${abc.def}", data));

        data = new HashMap();
        abc = new HashMap();
        data.put("abc", abc);
        data.put("abc.def", "789");
        abc.put("def","456");
        assertEquals("test var 456", evaluate("test var ${abc.def}", data));

        data = new HashMap();
        abc = new HashMap();
        data.put("abc", abc);
        data.put("abc.def", "789");
        assertEquals("test var 789", evaluate("test var ${abc.def}", data));

        data = new HashMap();
        abc = new HashMap();
        data.put("abc", abc);
        data.put("gh", "i");
        abc.put("dif","456");
        assertEquals("test var 456", evaluate("test var ${abc.d${gh}f}", data));


        data = new HashMap();
        abc = new HashMap();
        data.put("backend.rest", "http://localhost/rest");
        assertEquals("test var http://localhost/rest", evaluate("test var ${backend.rest}", data));


        //不能丢掉$后的大括号
        data = new HashMap();
        assertEquals("$}", evaluate("$}", data));

    }

    @Test
    public void testEvaluateTemplateCannotConflictWithJquery() throws Exception {
        Map data = new HashMap();
        assertEquals("$.extend", evaluate("$.extend", data));
    }


    private String evaluate(String template, Map data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        TemplateDecorator templateDecorator = new TemplateDecorator(out, data);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(templateDecorator));
        writer.write(template);
        writer.close();

        assertEquals("can gave length diff compare with input and output",out.toByteArray().length,template.getBytes().length+templateDecorator.getDiff());
        return out.toString();
    }
}

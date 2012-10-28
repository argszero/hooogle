package org.hooogle.filter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GenericRequestWrapper extends
        HttpServletRequestWrapper {
    private final InputStream inputStream;

    public GenericRequestWrapper(HttpServletRequest request) throws IOException {

        super(request);
        inputStream = new BufferedInputStream(request.getInputStream());
        inputStream.mark(Integer.MAX_VALUE);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        inputStream.reset();
        return new FilterServletInputStream(inputStream);
    }

    private static class FilterServletInputStream extends ServletInputStream {

        private InputStream stream;

        public FilterServletInputStream(InputStream inputStream) {
            stream = inputStream;
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public synchronized void reset() throws IOException {
            stream.reset();
        }
    }
}


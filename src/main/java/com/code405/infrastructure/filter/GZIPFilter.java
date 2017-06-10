package com.code405.infrastructure.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Peter Kozlovsky on 27.04.17.
 */
public class GZIPFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       //Not necessary
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String acceptEncoding = request.getHeader("accept-encoding");
            if (acceptEncoding != null && acceptEncoding.contains("gzip")) {
                GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);
                filterChain.doFilter(servletRequest, wrappedResponse);
                wrappedResponse.finishResponse();
                return;
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        //Not necessary
    }

    private static class GZIPResponseWrapper extends HttpServletResponseWrapper {
        protected HttpServletResponse origResponse;
        protected ServletOutputStream stream;
        protected PrintWriter writer;

        public GZIPResponseWrapper(HttpServletResponse response) {
            super(response);
            origResponse = response;
        }

        public ServletOutputStream createOutputStream() throws IOException {
            return new GZIPResponseStream(origResponse);
        }

        public void finishResponse() throws IOException {
            if (writer != null) {
                writer.close();
            } else {
                if (stream != null) {
                    stream.close();
                }
            }
        }

        public void flushBuffer() throws IOException {
            stream.flush();
        }

        public ServletOutputStream getOutputStream() throws IOException {
            if (writer != null) {
                throw new IllegalStateException("getWriter() has already been called!");
            }
            if (stream == null)
                stream = createOutputStream();
            return stream;
        }

        public PrintWriter getWriter() throws IOException {
            if (writer != null) {
                return writer;
            }
            if (stream != null) {
                throw new IllegalStateException("getOutputStream() has already been called!");
            }
            stream = createOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(stream, "UTF-8"));
            return writer;
        }
    }

    private static class GZIPResponseStream extends ServletOutputStream {
        protected ByteArrayOutputStream baos;
        protected GZIPOutputStream gzipStream;
        protected boolean closed;
        protected HttpServletResponse response;
        protected ServletOutputStream output;

        public GZIPResponseStream(HttpServletResponse response) throws IOException {
            super();
            closed = false;
            this.response = response;
            this.output = response.getOutputStream();
            baos = new ByteArrayOutputStream();
            gzipStream = new GZIPOutputStream(baos);
        }

        public void close() throws IOException {
            if (closed) {
                throw new IOException("This output stream has already been closed");
            }
            gzipStream.finish();
            byte[] bytes = baos.toByteArray();
            response.addHeader("Content-Length",
                    Integer.toString(bytes.length));
            response.addHeader("Content-Encoding", "gzip");
            output.write(bytes);
            output.flush();
            output.close();
            closed = true;
        }

        public void flush() throws IOException {
            if (closed) {
                throw new IOException("Cannot flush a closed output stream");
            }
            gzipStream.flush();
        }

        public void write(int b) throws IOException {
            if (closed) {
                throw new IOException("Cannot write to a closed output stream");
            }
            gzipStream.write((byte) b);
        }

        public void write(byte b[]) throws IOException {
            write(b, 0, b.length);
        }

        public void write(byte b[], int off, int len) throws IOException {
            if (closed) {
                throw new IOException("Cannot write to a closed output stream");
            }
            gzipStream.write(b, off, len);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            //Not necessary
        }
    }
}

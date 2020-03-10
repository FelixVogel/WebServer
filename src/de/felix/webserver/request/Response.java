/**
 MIT License

 Copyright (c) 2020 Felix Vogel

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package de.felix.webserver.request;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;

/**
 * Represents a outgoing response
 *
 * @author Felix Vogel
 */
public interface Response {

    void addCookie(final Cookie cookie);

    void addDateHeader(final String name, final long time);

    void addHeader(final String name, final String value);

    void addIntHeader(final String name, final int value);

    boolean containsHeader(final String name);

    String encodeRedirectURL(final String url);

    String encodeURL(final String url);

    Collection<String> getHeaderNames();

    Collection<String> getHeaders(final String name);

    int getStatus();

    void sendError(final int code) throws IOException;

    void sendError(final int code, final String message) throws IOException;

    void sendRedirect(final String redirect) throws IOException;

    void setDateHeader(final String name, long time);

    void setHeader(final String name, final String value);

    void setIntHeader(final String name, final int value);

    void setStatus(final int code);

    void flushBuffer() throws IOException;

    int getBufferSize();

    ServletOutputStream getOutputStream() throws IOException;

    PrintWriter getWriter() throws IOException;

    boolean isCommitted();

    void reset();

    void resetBuffer();

    void setBufferSize(final int size);

    void setCharacterEncoding(final String encoding);

    void setContentLength(final int length);

    void setContentLengthLong(final long length);

    void setContentType(final String type);

    void setLocale(final Locale locale);

}

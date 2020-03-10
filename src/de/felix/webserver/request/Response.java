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

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

	void addCookie(Cookie cookie);

	void addDateHeader(String name, long time);

	void addHeader(String name, String value);

	void addIntHeader(String name, int value);

	boolean containsHeader(String name);

	String encodeRedirectURL(String url);

	String encodeURL(String url);

	Collection<String> getHeaderNames();

	Collection<String> getHeaders(String name);

	int getStatus();

	void sendError(int code) throws IOException;

	void sendError(int code, String message) throws IOException;

	void sendRedirect(String redirect) throws IOException;

	void setDateHeader(String name, long time);

	void setHeader(String name, String value);

	void setIntHeader(String name, int value);

	void setStatus(int code);

	void flushBuffer() throws IOException;

	int getBufferSize();

	ServletOutputStream getOutputStream() throws IOException;

	PrintWriter getWriter() throws IOException;

	boolean isCommitted();

	void reset();

	void resetBuffer();

	void setBufferSize(int size);

	void setCharacterEncoding(String encoding) throws UnsupportedEncodingException;

	void setContentLength(int length);

	void setContentLengthLong(long length);

	void setContentType(String type);

	void setLocale(Locale locale);

}

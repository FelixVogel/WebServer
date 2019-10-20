package de.felix.webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

final class RequestImpl implements Request {

	private final HttpServletRequest req;
	private final Response resp;

	public RequestImpl(final HttpServletRequest req, final HttpServletResponse resp) {
		this.req = req;
		this.resp = new ResponseImpl(resp);
	}

	// Request section

	@Override
	public boolean authenticate(final HttpServletResponse resp) throws IOException, ServletException {
		return req.authenticate(resp);
	}

	@Override
	public String changeSessionId() {
		return req.changeSessionId();
	}

	@Override
	public String getAuthType() {
		return req.getAuthType();
	}

	@Override
	public String getContextPath() {
		return req.getContextPath();
	}

	@Override
	public Cookie[] getCookies() {
		return req.getCookies();
	}

	@Override
	public long getDateHeader(final String name) {
		return req.getDateHeader(name);
	}

	@Override
	public String getHeader(final String name) {
		return req.getHeader(name);
	}

	@Override
	public int getIntHeader(final String name) {
		return req.getIntHeader(name);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		return req.getHeaderNames();
	}

	@Override
	public Enumeration<String> getHeaders(final String name) {
		return req.getHeaders(name);
	}

	@Override
	public RequestMethod getMethod() {
		return RequestMethod.match(req.getMethod());
	}

	@Override
	public Part getPart(final String name) throws IOException, ServletException {
		return req.getPart(name);
	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		return req.getParts();
	}

	@Override
	public String getPathInfo() {
		return req.getPathInfo();
	}

	@Override
	public String getPathTranslated() {
		return req.getPathTranslated();
	}

	@Override
	public String getQueryString() {
		return req.getQueryString();
	}

	@Override
	public String getRemoteUser() {
		return req.getRemoteUser();
	}

	@Override
	public String getRequestURI() {
		return req.getRequestURI();
	}

	@Override
	public StringBuffer getRequestURL() {
		return req.getRequestURL();
	}

	@Override
	public String getRequestedSessionId() {
		return req.getRequestedSessionId();
	}

	@Override
	public String getServletPath() {
		return req.getServletPath();
	}

	@Override
	public HttpSession getSession() {
		return req.getSession();
	}

	@Override
	public HttpSession getSession(final boolean create) {
		return req.getSession(create);
	}

	@Override
	public Principal getUserPrincipal() {
		return req.getUserPrincipal();
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return req.isRequestedSessionIdFromCookie();
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return req.isRequestedSessionIdFromURL();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return req.isRequestedSessionIdValid();
	}

	@Override
	public boolean isUserInRole(final String role) {
		return req.isUserInRole(role);
	}

	@Override
	public void login(final String user, final String password) throws ServletException {
		req.login(user, password);
	}

	@Override
	public void logout() throws ServletException {
		req.logout();
	}

	@Override
	public <T extends HttpUpgradeHandler> T upgrade(final Class<T> arg0) throws IOException, ServletException {
		return req.upgrade(arg0);
	}

	@Override
	public AsyncContext getAsyncContext() {
		return req.getAsyncContext();
	}

	@Override
	public Object getAttribute(final String name) {
		return req.getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return req.getAttributeNames();
	}

	@Override
	public String getCharacterEncoding() {
		return req.getCharacterEncoding();
	}

	@Override
	public int getContentLength() {
		return req.getContentLength();
	}

	@Override
	public long getContentLengthLong() {
		return req.getContentLengthLong();
	}

	@Override
	public String getContentType() {
		return req.getContentType();
	}

	@Override
	public DispatcherType getDispatcherType() {
		return req.getDispatcherType();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return req.getInputStream();
	}

	@Override
	public String getLocalAddr() {
		return req.getLocalAddr();
	}

	@Override
	public String getLocalName() {
		return req.getLocalName();
	}

	@Override
	public int getLocalPort() {
		return req.getLocalPort();
	}

	@Override
	public Locale getLocale() {
		return req.getLocale();
	}

	@Override
	public Enumeration<Locale> getLocales() {
		return req.getLocales();
	}

	@Override
	public String getParameter(final String name) {
		return req.getParameter(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return req.getParameterMap();
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return req.getParameterNames();
	}

	@Override
	public String[] getParameterValues(final String names) {
		return req.getParameterValues(names);
	}

	@Override
	public String getProtocol() {
		return req.getProtocol();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return req.getReader();
	}

	@Override
	public String getRemoteAddr() {
		return req.getRemoteAddr();
	}

	@Override
	public String getRemoteHost() {
		return req.getRemoteHost();
	}

	@Override
	public int getRemotePort() {
		return req.getRemotePort();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(final String name) {
		return req.getRequestDispatcher(name);
	}

	@Override
	public Response getResponse() {
		return resp;
	}

	@Override
	public String getScheme() {
		return req.getScheme();
	}

	@Override
	public String getServerName() {
		return req.getServerName();
	}

	@Override
	public int getServerPort() {
		return req.getServerPort();
	}

	@Override
	public ServletContext getServletContext() {
		return req.getServletContext();
	}

	@Override
	public boolean isAsyncStarted() {
		return req.isAsyncStarted();
	}

	@Override
	public boolean isAsyncSupported() {
		return req.isAsyncSupported();
	}

	@Override
	public boolean isSecure() {
		return req.isSecure();
	}

	@Override
	public void removeAttribute(final String name) {
		req.removeAttribute(name);
	}

	@Override
	public void setAttribute(final String name, final Object value) {
		req.setAttribute(name, value);
	}

	@Override
	public void setCharacterEncoding(final String encoding) throws UnsupportedEncodingException {
		req.setCharacterEncoding(encoding);
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		return req.startAsync();
	}

	@Override
	public AsyncContext startAsync(final ServletRequest req, final ServletResponse resp) throws IllegalStateException {
		return req.startAsync(req, resp);
	}

	// Response Section
	class ResponseImpl implements Response {

		private final HttpServletResponse resp;

		public ResponseImpl(final HttpServletResponse resp) {
			this.resp = resp;
		}

		@Override
		public void addCookie(final Cookie cookie) {
			resp.addCookie(cookie);
		}

		@Override
		public void addDateHeader(final String name, final long time) {
			resp.addDateHeader(name, time);
		}

		@Override
		public void addHeader(final String name, final String value) {
			resp.addHeader(name, value);
		}

		@Override
		public void addIntHeader(final String name, final int value) {
			resp.addIntHeader(name, value);
		}

		@Override
		public boolean containsHeader(final String name) {
			return resp.containsHeader(name);
		}

		@Override
		public String encodeRedirectURL(final String url) {
			return resp.encodeRedirectURL(url);
		}

		@Override
		public String encodeURL(final String url) {
			return resp.encodeURL(url);
		}

		@Override
		public Collection<String> getHeaderNames() {
			return resp.getHeaderNames();
		}

		@Override
		public Collection<String> getHeaders(final String name) {
			return resp.getHeaders(name);
		}

		@Override
		public int getStatus() {
			return resp.getStatus();
		}

		@Override
		public void sendError(final int code) throws IOException {
			resp.sendError(code);
		}

		@Override
		public void sendError(final int code, final String message) throws IOException {
			resp.sendError(code, message);
		}

		@Override
		public void sendRedirect(final String redirect) throws IOException {
			resp.sendRedirect(redirect);
		}

		@Override
		public void setDateHeader(final String name, final long time) {
			resp.setDateHeader(name, time);
		}

		@Override
		public void setHeader(final String name, final String value) {
			resp.setHeader(name, value);
		}

		@Override
		public void setIntHeader(final String name, final int value) {
			resp.setIntHeader(name, value);
		}

		@Override
		public void setStatus(final int code) {
			resp.setStatus(code);
		}

		@Override
		public void flushBuffer() throws IOException {
			resp.flushBuffer();
		}

		@Override
		public int getBufferSize() {
			return resp.getBufferSize();
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return resp.getOutputStream();
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			return resp.getWriter();
		}

		@Override
		public boolean isCommitted() {
			return resp.isCommitted();
		}

		@Override
		public void reset() {
			resp.reset();
		}

		@Override
		public void resetBuffer() {
			resp.resetBuffer();
		}

		@Override
		public void setBufferSize(final int size) {
			resp.setBufferSize(size);
		}

		@Override
		public void setCharacterEncoding(final String encoding) throws UnsupportedEncodingException {
			resp.setCharacterEncoding(encoding);
		}

		@Override
		public void setContentLength(final int length) {
			resp.setContentLength(length);
		}

		@Override
		public void setContentLengthLong(final long length) {
			resp.setContentLengthLong(length);
		}

		@Override
		public void setContentType(final String type) {
			resp.setContentType(type);
		}

		@Override
		public void setLocale(final Locale locale) {
			resp.setLocale(locale);
		}

	}

}

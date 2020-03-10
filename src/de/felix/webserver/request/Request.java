package de.felix.webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
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
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

/**
 * This class represents a incoming request
 *
 * @author Felix Vogel
 */
public interface Request {

    boolean authenticate(HttpServletResponse resp) throws IOException, ServletException;

    String changeSessionId();

    String getAuthType();

    String getContextPath();

    Cookie[] getCookies();

    long getDateHeader(String name);

    String getHeader(String name);

    int getIntHeader(String name);

    Enumeration<String> getHeaderNames();

    Enumeration<String> getHeaders(String name);

    RequestMethod getMethod();

    Part getPart(String name) throws IOException, ServletException;

    Collection<Part> getParts() throws IOException, ServletException;

    String getPathInfo();

    String getPathTranslated();

    String getQueryString();

    String getRemoteUser();

    String getRequestURI();

    StringBuffer getRequestURL();

    String getRequestedSessionId();

    String getServletPath();

    HttpSession getSession();

    HttpSession getSession(boolean create);

    Principal getUserPrincipal();

    boolean isRequestedSessionIdFromCookie();

    boolean isRequestedSessionIdFromURL();

    boolean isRequestedSessionIdValid();

    boolean isUserInRole(String role);

    void login(String user, String password) throws ServletException;

    void logout() throws ServletException;

    <T extends HttpUpgradeHandler> T upgrade(Class<T> arg0) throws IOException, ServletException;

    AsyncContext getAsyncContext();

    Object getAttribute(String name);

    Enumeration<String> getAttributeNames();

    String getCharacterEncoding();

    int getContentLength();

    long getContentLengthLong();

    String getContentType();

    DispatcherType getDispatcherType();

    ServletInputStream getInputStream() throws IOException;

    String getLocalAddr();

    String getLocalName();

    int getLocalPort();

    Locale getLocale();

    Enumeration<Locale> getLocales();

    String getParameter(String name);

    Map<String, String[]> getParameterMap();

    Enumeration<String> getParameterNames();

    String[] getParameterValues(String names);

    String getProtocol();

    BufferedReader getReader() throws IOException;

    String getRemoteAddr();

    String getRemoteHost();

    int getRemotePort();

    RequestDispatcher getRequestDispatcher(String name);

    Response getResponse();

    String getScheme();

    String getServerName();

    int getServerPort();

    ServletContext getServletContext();

    boolean isAsyncStarted();

    boolean isAsyncSupported();

    boolean isSecure();

    void removeAttribute(String name);

    void setAttribute(String name, Object value);

    void setCharacterEncoding(String encoding) throws UnsupportedEncodingException;

    AsyncContext startAsync() throws IllegalStateException;

    AsyncContext startAsync(ServletRequest req, ServletResponse resp) throws IllegalStateException;

}

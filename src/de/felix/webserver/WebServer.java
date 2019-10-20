package de.felix.webserver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.SecuredRedirectHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import de.felix.webserver.auth.AuthenticationStrategy;
import de.felix.webserver.request.MethodContainer;
import de.felix.webserver.request.MethodExecServlet;
import de.felix.webserver.request.PathHandler;
import de.felix.webserver.request.RequestHandler;
import de.felix.webserver.request.RequestMethod;
import de.felix.webserver.request.Response;

/**
 * The {@link WebServer}
 *
 * @author Felix Vogel
 */
public class WebServer {

	public static void main(final String[] args) {
		new WebServer().start();
	}

	private final Server server;
	private final Configuration config;
	private final Map<String, List<ServletHolder>> servlets = new HashMap<>();

	private boolean running = false;

	public WebServer() {
		System.out.println("Running on " + System.getProperty("os.name") + " " + System.getProperty("os.arch"));

		if(!System.getProperty("os.name").toLowerCase().matches(".*(nix|nux|aix|win).*")) {
			System.err.println("Your OS is not Supported!");

			System.exit(0);
		}

		config = new Configuration();
		server = new Server();
	}

	/**
	 * Start the {@link WebServer}
	 */
	public final void start() {
		if(running) {
			return;
		}

		running = true;

		createServer();

		try {
			server.start();

			onStart();
		} catch(final Exception e) {
			e.printStackTrace();
		}
	}

	private void createServer() {

		final boolean hasHTTPSConfigured = config.httpsPort > 0 &&
				config.sslFile != null &&
				config.sslFile.isEmpty();

		final List<ServerConnector> connectors = new ArrayList<>();

		// HTTP Configuration
		final HttpConfiguration http_config = new HttpConfiguration();

		if(hasHTTPSConfigured) {
			http_config.setSecureScheme("https");
			http_config.setSecurePort(config.httpsPort);
		}

		http_config.setOutputBufferSize(32768);
		http_config.setRequestHeaderSize(8192);
		http_config.setResponseHeaderSize(8192);
		http_config.setSendServerVersion(false);
		http_config.setSendDateHeader(false);
		http_config.setSendXPoweredBy(false);

		// HTTP Server connector
		final ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(http_config));

		http.setPort(config.httpPort);
		http.setIdleTimeout(config.timeout * 60 * 1000);

		connectors.add(http);

		if(hasHTTPSConfigured) {
			// Getting the SSL keystore File
			final File ssl_keystore = new File(config.sslFile);

			if(ssl_keystore.exists() && ssl_keystore.canRead()) {
				System.out.println("Configuring HTTPS");

				// Creating SSL for HTTPS
				final SslContextFactory sslContextFactory = new SslContextFactory.Server();

				sslContextFactory.setKeyStorePath(ssl_keystore.getAbsolutePath());
				sslContextFactory.setKeyStorePassword(config.keystorepass);
				sslContextFactory.setKeyManagerPassword(config.keymanagerpass);
				sslContextFactory.setTrustStorePath(ssl_keystore.getAbsolutePath());
				sslContextFactory.setTrustStorePassword(config.truststorepass);
				sslContextFactory.setExcludeCipherSuites("SSL_RSA_WITH_DES_CBC_SHA", "SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA", "SSL_RSA_EXPORT_WITH_RC4_40_MD5", "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");
				sslContextFactory.setUseCipherSuitesOrder(true);

				final SslConnectionFactory sslcon = new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString());

				// HTTPS Configuration
				final HttpConfiguration https_config = new HttpConfiguration(http_config);
				final SecureRequestCustomizer src = new SecureRequestCustomizer();

				src.setStsMaxAge(2000);
				src.setStsIncludeSubDomains(true);

				https_config.addCustomizer(src);

				// HTTPS Server connector
				final ServerConnector https = new ServerConnector(server, sslcon, new HttpConnectionFactory(https_config));

				https.setIdleTimeout(config.timeout * 60 * 1000);
				https.setPort(config.httpsPort);

				connectors.add(https);
			} else {
				System.err.println("No SSL Keystore, ignoring HTTPS");
			}
		}

		// Applying the connectors to the Server
		server.setConnectors(connectors.toArray(new Connector[connectors.size()]));

		final GzipHandler gzip = new GzipHandler();

		server.setHandler(gzip);

		final HandlerCollection handlers = new HandlerCollection();

		if(hasHTTPSConfigured) {
			handlers.addHandler(new SecuredRedirectHandler());
		}

		// Servlet's
		final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

		context.getSessionHandler().setMaxInactiveInterval(config.timeout * 60);
		context.setContextPath("/");

		servlets.forEach((path, _handlers) -> {
			for(final ServletHolder handler : _handlers) {
				context.addServlet(handler, path);
			}
		});

		servlets.clear();

		handlers.addHandler(context);
		handlers.addHandler(new DefaultHandler());

		final RewriteHandler rewrite = new RewriteHandler();

		rewrite.setHandler(server.getHandler());

		server.setHandler(rewrite);

		gzip.setHandler(handlers);

		server.setStopAtShutdown(true);

		Runtime.getRuntime().addShutdownHook(new ShutdownThread(this));
	}

	/**
	 * This method gets called after the {@link WebServer} has started
	 */
	public void onStart() {}

	/**
	 * Stop the {@link WebServer}
	 */
	public final void stop() {
		if(!running) {
			return;
		}

		try {
			server.stop();

			onStop();
		} catch(final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method gets called after the {@link WebServer} has stopped
	 */
	public void onStop() {}

	/**
	 * Get the {@link WebServer} configuration
	 *
	 * @return The {@link Configuration} of the {@link WebServer}
	 */
	public final Configuration getConfig() {
		return config;
	}

	final class ShutdownThread extends Thread {

		private final WebServer server;

		public ShutdownThread(final WebServer server) {
			this.server = server;
		}

		@Override
		public void run() {
			System.out.println("Stopping...");
			server.stop();
		}

	}

	/**
	 * Register a {@link HttpServlet}
	 *
	 * @param path The path of the {@link HttpServlet}
	 * @param servlet The {@link ServletHolder} to registers
	 */
	public void registerServlet(final String path, final HttpServlet servlet) {
		if(running || path == null || path.isEmpty() || servlet == null) {
			return;
		}

		if(!servlets.containsKey(path)) {
			servlets.put(path, new LinkedList<>());
		}

		servlets.get(path).add(new ServletHolder(servlet));
	}

	/**
	 * Register {@link RequestHandler}'s to the WebServer
	 *
	 * @param handlerContainer A container class containing annotated {@link RequestHandler} methods
	 */
	public void registerHandlers(final PathHandler handlerContainer) {
		final List<Method> methods = Arrays.asList(handlerContainer.getClass().getMethods()).stream().filter(m -> m.isAnnotationPresent(RequestHandler.class)).collect(Collectors.toList());

		final Map<String, Map<RequestMethod, List<MethodContainer>>> sorted = new HashMap<>();

		for(final Method m : methods) {
			final RequestHandler rh = m.getAnnotation(RequestHandler.class);
			final String pathSpec = handlerContainer.setPathPrefix() + rh.path();

			if(sorted.get(pathSpec) == null) {
				sorted.put(pathSpec, Util.initHandleMap());

				System.err.println("Init: `" + pathSpec + "`");
			}

			AuthenticationStrategy authenticationStrategy = null;

			try {
				authenticationStrategy = (AuthenticationStrategy)Class.forName(rh.strategy()).getDeclaredConstructor().newInstance();
			} catch(final Exception e) {
				e.printStackTrace();
			}

			sorted.get(pathSpec).get(rh.method()).add(new MethodContainer(handlerContainer, m, authenticationStrategy));

			// registerHandler(new ServletHolder(mes), handlerContainer.setPathPrefix() + rh.path());
		}

		for(final Entry<String, Map<RequestMethod, List<MethodContainer>>> entry : sorted.entrySet()) {
			registerServlet(entry.getKey(), new MethodExecServlet(entry.getValue()));
		}
	}

	/**
	 * Send a {@link File}
	 *
	 * @param path The path of the {@link File}
	 * @param resp The {@link HttpServletResponse} to send the {@link File} to
	 * @throws IOException {@link IOException} of the {@link HttpServletResponse}
	 */
	public static void sendFile(final String path, final Response resp) throws IOException {
		sendFile(new File(path), resp);
	}

	/**
	 * Send a {@link File}
	 *
	 * @param file The {@link File} to send
	 * @param resp The {@link HttpServletResponse} to send the {@link File} to
	 * @throws IOException {@link IOException} of the {@link HttpServletResponse}
	 */
	public static void sendFile(final File file, final Response resp) throws IOException {
		if(file.isFile() && file.exists()) {
			final String mime = MimeType.getMimeType(file);

			resp.setContentType(mime);
			resp.setStatus(200);

			resp.setContentLengthLong(file.length());

			Files.copy(file.toPath(), resp.getOutputStream());
		} else {
			resp.setStatus(404);
		}
	}

	public static final class Configuration {

		protected int timeout = 15, httpPort = 80, httpsPort = 443;
		protected String sslFile, keystorepass, keymanagerpass, truststorepass;

		public void setTimeout(final int timeout) {
			this.timeout = timeout;
		}

		public void setHttpPort(final int httpPort) {
			this.httpPort = httpPort;
		}

		public void setHttpsPort(final int httpsPort) {
			this.httpsPort = httpsPort;
		}

		public void setSSLFileLocation(final String sslFile) {
			this.sslFile = sslFile;
		}

		public void setKeystorePass(final String keystorePass) {
			this.keystorepass = keystorePass;
		}

		public void setKeymanagerPass(final String keymanagerPass) {
			this.keymanagerpass = keymanagerPass;
		}

		public void setTruststorePass(final String truststorePass) {
			this.truststorepass = truststorePass;
		}

	}

}
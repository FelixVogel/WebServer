package de.felix.webserver.request;

/**
 * The types of request methods that can be received
 *
 * @author Felix Vogel
 */
public enum RequestMethod {

	/**
	 * @deprecated This won't be registered anymore
	 */
	@Deprecated
	ALL("all"),
	DELETE("delete"),
	GET("get"),
	HEAD("head"),
	OPTION("options"),
	POST("post"),
	PUT("put");

	private final int hc;

	private RequestMethod(final String method) {
		this.hc = method.hashCode();
	}

	public static RequestMethod match(final String input) {
		for(final RequestMethod rm : values()) {
			if(rm.hc == input.toLowerCase().hashCode()) {
				return rm;
			}
		}

		return null;
	}

}

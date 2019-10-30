package de.felix.webserver.request;

/**
 * The types of request methods that can be received
 *
 * @author Felix Vogel
 */
public enum RequestMethod {

	/**
	 * Catch all requests with type of DELETE
	 */
	DELETE,

	/**
	 * Catch all requests with type of GET
	 */
	GET,

	/**
	 * Catch all requests with type of HEAD
	 */
	HEAD,

	/**
	 * Catch all requests with type of OPTION
	 */
	OPTION,

	/**
	 * Catch all requests with type of POST
	 */
	POST,

	/**
	 * Catch all requests with type of PUT
	 */
	PUT;

	private final int method;

	RequestMethod() {
		this.method = name().hashCode();
	}

	/**
	 * Match the given subject to a request method
	 *
	 * @param subject The subject to match
	 * @return The {@link RequestMethod} that matched or null if none matched
	 */
	public static RequestMethod match(final String subject) {
		final int sh = subject.toLowerCase().hashCode();

		for(final RequestMethod rm : values()) {
			if(rm.method == sh) {
				return rm;
			}
		}

		return null;
	}

}

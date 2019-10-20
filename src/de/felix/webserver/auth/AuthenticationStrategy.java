package de.felix.webserver.auth;

import de.felix.webserver.request.Request;

/**
 * A authentication strategy basically represents a way of authentication for a request to be executed
 *
 * @author Felix Vogel
 */
public interface AuthenticationStrategy {

	/**
	 * Authenticate the request, if valid it should return true
	 *
	 * @param request The request to authenticate
	 * @return true: If the request is authenticated
	 */
	boolean authenticate(Request request);

}

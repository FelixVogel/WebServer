package de.felix.webserver.auth;

import de.felix.webserver.request.Request;

/**
 * This represents a <br> A<code>(Always)</code>A<code>(Authenticated)</code>Strategy
 *
 * @author Felix Vogel
 */
public class AAStrategy implements AuthenticationStrategy {

	@Override
	public boolean authenticate(final Request request) {
		return true;
	}

}

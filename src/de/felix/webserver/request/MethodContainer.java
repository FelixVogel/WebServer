package de.felix.webserver.request;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Felix Vogel
 */
public class MethodContainer {

	private final Object caller;
	private final Method method;

	public MethodContainer(final Object caller, final Method method) {
		this.caller = caller;
		this.method = method;
	}

	public void call(final Request request) {
		try {
			method.invoke(caller, request);
		} catch(final IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}

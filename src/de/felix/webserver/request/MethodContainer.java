package de.felix.webserver.request;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.felix.webserver.auth.AuthenticationStrategy;

public class MethodContainer {

	private final Object caller;
	private final Method method;
	private final AuthenticationStrategy strategy;

	public MethodContainer(final Object caller, final Method method, final AuthenticationStrategy strategy) {
		this.caller = caller;
		this.method = method;
		this.strategy = strategy;
	}

	public void call(final Request request) {
		if(strategy == null || !strategy.authenticate(request)) {
			return;
		}

		try {
			method.invoke(caller, request);
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}

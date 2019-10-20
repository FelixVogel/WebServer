package de.felix.webserver.request;

/**
 * THis represents a function that can be registered to the function path of the {@link de.felix.webserver.WebServer}
 *
 * @author Felix Vogel
 */
public interface Function {

    boolean canExecute(final Request request);

    void execute(final Request request);

}

package de.felix.webserver.request;

/**
 * THis represents a function that can be registered to the function path of the {@link de.felix.webserver.WebServer}
 *
 * @author Felix Vogel
 */
public interface Function {

    default boolean canExecute(final Request request) {
        return true;
    }

    void execute(final Request request);

}

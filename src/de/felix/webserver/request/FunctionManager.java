package de.felix.webserver.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Felix Vogel
 */
public final class FunctionManager implements PathHandler {

    private static final Map<String, Function> FUNCTIONS = new HashMap<>();

    public static void registerFunction(final String name, final Function function) {
        if (FUNCTIONS.containsKey(name)) {
            throw new IllegalArgumentException("Function " + name + " has already been registered once");
        }

        FUNCTIONS.put(name, function);
    }

    protected static Function get(final String name) {
        return FUNCTIONS.get(name);
    }

    // Class

    private final String path;

    public FunctionManager(final String path) {
        this.path = path;
    }

    @Override
    public String setPathPrefix() {
        return path;
    }

    @RequestHandler(path = "/func", method = RequestMethod.DELETE)
    public void handleFunctionDELETE(final Request request) {
        handleFunction(request);
    }


    @RequestHandler(path = "/func", method = RequestMethod.GET)
    public void handleFunctionGET(final Request request) {
        handleFunction(request);
    }

    @RequestHandler(path = "/func", method = RequestMethod.HEAD)
    public void handleFunctionHEAD(final Request request) {
        handleFunction(request);
    }

    @RequestHandler(path = "/func", method = RequestMethod.OPTION)
    public void handleFunctionOPTION(final Request request) {
        handleFunction(request);
    }

    @RequestHandler(path = "/func/*", method = RequestMethod.POST)
    public void handleFunctionPOST(final Request request) {
        handleFunction(request);
    }

    @RequestHandler(path = "/func/*", method = RequestMethod.PUT)
    public void handleFunctionPUT(final Request request) {
        handleFunction(request);
    }

    public void handleFunction(final Request request) {
        final String name = request.getParameter("name");

        if (name != null && !name.isEmpty()) {
            final Function func = get(name);

            if (func != null) {
                if (func.canExecute(request)) {
                    func.execute(request);
                } else {
                    request.getResponse().setStatus(401);
                }
            } else {
                request.getResponse().setStatus(404);
            }
        }
    }

}

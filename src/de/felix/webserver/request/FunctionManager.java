package de.felix.webserver.request;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Felix Vogel
 */
public final class FunctionManager implements PathHandler {

    private static final Map<String, Function> FUNCTIONS = new HashMap<>();
    private static final Map<String, ScriptCache> SCRIPT_CACHE = new HashMap<>();

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

    private final ScriptEngine engine;

    public FunctionManager() {
        this.engine = new ScriptEngineManager().getEngineByName("nashorn");
    }

    @Override
    public String setPathPrefix() {
        return "";
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

    @RequestHandler(path = "/func/", method = RequestMethod.POST)
    public void handleFunctionPOST(final Request request) {
        handleFunction(request);
    }

    @RequestHandler(path = "/func/", method = RequestMethod.PUT)
    public void handleFunctionPUT(final Request request) {
        handleFunction(request);
    }

    public void handleFunction(final Request request) {
        final String name = request.getParameter("name");

        if (name != null && !name.isEmpty()) {
            if (name.startsWith("js")) {
                final String path = "scripts/" + name + ".js";
                final File sf = new File(path);

                ScriptCache sc = null;

                if (SCRIPT_CACHE.containsKey(path)) {
                    sc = SCRIPT_CACHE.get(path);

                    if (sf.lastModified() != sc.lastMod) {
                        System.err.println("Reloaded script: " + path);
                        sc = new ScriptCache(sf);
                        SCRIPT_CACHE.put(path, sc);
                    }
                } else {
                    System.err.println("Loaded script: " + path);
                    sc = new ScriptCache(sf);
                    SCRIPT_CACHE.put(path, sc);
                }

                try {
                    engine.eval(sc.script);
                } catch (ScriptException e) {
                    request.getResponse().setStatus(500);
                }

                final Invocable invocable = (Invocable) engine;

                try {
                    invocable.invokeFunction("handleRequest", request);
                } catch (ScriptException | NoSuchMethodException e) {
                    request.getResponse().setStatus(500);
                }
            } else {
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
        } else {
            request.getResponse().setStatus(404);
        }
    }

    static class ScriptCache {

        private final String script;
        private final long lastMod;

        public ScriptCache(final File file) {
            this.lastMod = file.lastModified();
            this.script = read(file);
        }

        private String read(final File file) {
            StringBuilder content = new StringBuilder();

            try (final FileReader fr = new FileReader(file)) {
                int c;
                while ((c = fr.read()) != -1) {
                    content.append((char) c);
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }

            return content.toString();
        }

    }

}

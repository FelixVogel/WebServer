/**
 MIT License

 Copyright (c) 2020 Felix Vogel

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package de.felix.webserver.request;

import de.felix.script.FunctionEngine;

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
    private static final Map<String, FunctionEngine> SCRIPT_CACHE = new HashMap<>();

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

    @Override
    public String setPathPrefix() {
        return "/func";
    }

    @RequestHandler(path = "", method = RequestMethod.DELETE)
    public void handleFunctionDELETE(final Request request) {
        handleFunction(request);
    }


    @RequestHandler(path = "", method = RequestMethod.GET)
    public void handleFunctionGET(final Request request) {
        handleFunction(request);
    }

    @RequestHandler(path = "", method = RequestMethod.HEAD)
    public void handleFunctionHEAD(final Request request) {
        handleFunction(request);
    }

    @RequestHandler(path = "", method = RequestMethod.OPTION)
    public void handleFunctionOPTION(final Request request) {
        handleFunction(request);
    }

    @RequestHandler(path = "/", method = RequestMethod.POST)
    public void handleFunctionPOST(final Request request) {
        handleFunction(request);
    }

    @RequestHandler(path = "/", method = RequestMethod.PUT)
    public void handleFunctionPUT(final Request request) {
        handleFunction(request);
    }

    public void handleFunction(final Request request) {
        final String name = request.getParameter("name");

        if (name != null && !name.isEmpty()) {
            if (name.startsWith("js")) {
                final String path = "scripts/" + name + ".js";
                final File sf = new File(path);

                FunctionEngine functionEngine = null;

                if (SCRIPT_CACHE.containsKey(path)) {
                    functionEngine = SCRIPT_CACHE.get(path);
                } else {
                    functionEngine = new FunctionEngine(new File(path));
                    SCRIPT_CACHE.put(path, functionEngine);
                }

                functionEngine.reload();

                functionEngine.callFunction("handleRequest", request);
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

}

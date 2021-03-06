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
package de.felix.webserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.felix.webserver.request.MethodContainer;
import de.felix.webserver.request.Request;
import de.felix.webserver.request.RequestMethod;
import de.felix.webserver.request.Response;

/**
 * This class keeps some utilities
 *
 * @author Felix Vogel
 */
public final class Util {

    // Authentication

    /**
     * Require basic authentication over the client that is trying to access the resource
     *
     * @param response The response to send the authentication request to
     * @param name     The name of the application
     */
    public static void sendBasicAuthenticationRequired(final Response response, final String name) {
        response.setHeader("WWW-Authenticate", "Basic realm=\"" + name + "\"");
        response.setStatus(401);
    }

    // Headers

    /**
     * Check Headers of a {@link Request}
     *
     * @param req     The {@link Request} to check
     * @param headers The Headers to check
     * @return true: If none of the Headers are null or empty
     */
    public static boolean checkHeaders(final Request req, final String... headers) {
        if (headers == null || headers.length < 1) {
            return false;
        }

        for (final String n : headers) {
            if (n == null || n.isEmpty()) {
                continue;
            }

            if (req.getHeader(n) == null || req.getHeader(n).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    // Path

    /**
     * Check a {@link Request} path
     *
     * @param req  The {@link Request} to check
     * @param path The path to match
     * @return true: If the path matches the request path
     */
    public static boolean checkPath(final Request req, final String path) {
        return req.getServletPath().endsWith(path);
    }

    // Combined

    /**
     * Check the Headers of a {@link Request} and its pat
     *
     * @param req     The {@link Request} to check
     * @param path    The path to match
     * @param headers The Headers to check
     * @return true: If none of the Headers are null or empty and if the path matches the request path
     */
    public static boolean checkPathHeaders(final Request req, final String path, final String... headers) {
        return checkPath(req, path) && checkHeaders(req, headers);
    }

    // internal utility

    protected static Map<RequestMethod, List<MethodContainer>> initMethodMap() {
        final Map<RequestMethod, List<MethodContainer>> map = new HashMap<>(6);

        // map.put(RequestMethod.ALL, new ArrayList<>());
        map.put(RequestMethod.DELETE, new ArrayList<>());
        map.put(RequestMethod.GET, new ArrayList<>());
        map.put(RequestMethod.HEAD, new ArrayList<>());
        map.put(RequestMethod.OPTION, new ArrayList<>());
        map.put(RequestMethod.POST, new ArrayList<>());
        map.put(RequestMethod.PUT, new ArrayList<>());

        return Collections.unmodifiableMap(map);
    }

}

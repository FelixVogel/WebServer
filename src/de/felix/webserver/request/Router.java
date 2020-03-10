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

import de.felix.webserver.WebServer;

import java.io.File;
import java.io.IOException;

/**
 * @author Felix Vogel
 */
public class Router implements PathHandler {

    @Override
    public String setPathPrefix() {
        return "";
    }

    @RequestHandler(path = "/*", method = RequestMethod.GET)
    public void onGet(final Request request) {
        String path = request.getRequestURI().substring(1);

        if (!path.matches(".+\\..+")) {
            path += (path.endsWith("/") ? "" : "/") + "index.html";
        }

        path = ("web/" + path).replace("//", "/");

        try {
            WebServer.sendFile(new File(path), request.getResponse());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}

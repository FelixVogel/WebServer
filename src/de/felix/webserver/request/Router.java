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

        System.out.println(path);

        try {
            WebServer.sendFile(new File("web/" + path), request.getResponse());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}

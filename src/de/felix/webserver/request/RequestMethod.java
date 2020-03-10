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

/**
 * The types of request methods that can be received
 *
 * @author Felix Vogel
 */
public enum RequestMethod {

    /**
     * Catch all requests with type of DELETE
     */
    DELETE,

    /**
     * Catch all requests with type of GET
     */
    GET,

    /**
     * Catch all requests with type of HEAD
     */
    HEAD,

    /**
     * Catch all requests with type of OPTION
     */
    OPTION,

    /**
     * Catch all requests with type of POST
     */
    POST,

    /**
     * Catch all requests with type of PUT
     */
    PUT;

    private final int method;

    RequestMethod() {
        this.method = name().hashCode();
    }

    /**
     * Match the given subject to a request method
     *
     * @param subject The subject to match
     * @return The {@link RequestMethod} that matched or null if none matched
     */
    public static RequestMethod match(final String subject) {
        final int sh = subject.toLowerCase().hashCode();

        for (final RequestMethod rm : values()) {
            if (rm.method == sh) {
                return rm;
            }
        }

        return null;
    }

}

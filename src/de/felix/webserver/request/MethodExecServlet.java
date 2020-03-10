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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * This servlet manages {@link RequestHandler} methods
 *
 * @author Felix Vogel
 */
@SuppressWarnings("serial")
public class MethodExecServlet extends HttpServlet {

    private final MethodContainer[] delete, get, head, options, post, put;

    public MethodExecServlet(final Map<RequestMethod, List<MethodContainer>> sorted) {
        this.delete = sorted.get(RequestMethod.DELETE).toArray(MethodContainer[]::new);
        this.get = sorted.get(RequestMethod.GET).toArray(MethodContainer[]::new);
        this.head = sorted.get(RequestMethod.HEAD).toArray(MethodContainer[]::new);
        this.options = sorted.get(RequestMethod.OPTION).toArray(MethodContainer[]::new);
        this.post = sorted.get(RequestMethod.POST).toArray(MethodContainer[]::new);
        this.put = sorted.get(RequestMethod.PUT).toArray(MethodContainer[]::new);
    }

    @Override
    protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp) {
        if (delete != null && delete.length > 0) {
            for (final MethodContainer container : delete) {
                container.call(new RequestImpl(req, resp));
            }
        }
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        if (get != null && get.length > 0) {
            for (final MethodContainer container : get) {
                container.call(new RequestImpl(req, resp));
            }
        }
    }

    @Override
    protected void doHead(final HttpServletRequest req, final HttpServletResponse resp) {
        if (head != null && head.length > 0) {
            for (final MethodContainer container : head) {
                container.call(new RequestImpl(req, resp));
            }
        }
    }

    @Override
    protected void doOptions(final HttpServletRequest req, final HttpServletResponse resp) {
        if (options != null && options.length > 0) {
            for (final MethodContainer container : options) {
                container.call(new RequestImpl(req, resp));
            }
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        if (post != null && post.length > 0) {
            for (final MethodContainer container : post) {
                container.call(new RequestImpl(req, resp));
            }
        }
    }

    @Override
    protected void doPut(final HttpServletRequest req, final HttpServletResponse resp) {
        if (put != null && put.length > 0) {
            for (final MethodContainer container : put) {
                container.call(new RequestImpl(req, resp));
            }
        }
    }

    @Override
    protected void doTrace(final HttpServletRequest arg0, final HttpServletResponse arg1) {
        arg1.setStatus(403);
    }

}

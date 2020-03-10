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
        // @formatter:off
		this.delete  = sorted.get(RequestMethod.DELETE).toArray(new MethodContainer[0]);
		this.get     = sorted.get(RequestMethod.GET).toArray(new MethodContainer[0]);
		this.head    = sorted.get(RequestMethod.HEAD).toArray(new MethodContainer[0]);
		this.options = sorted.get(RequestMethod.OPTION).toArray(new MethodContainer[0]);
		this.post    = sorted.get(RequestMethod.POST).toArray(new MethodContainer[0]);
		this.put     = sorted.get(RequestMethod.PUT).toArray(new MethodContainer[0]);
		// @formatter:on
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

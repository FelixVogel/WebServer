package de.felix.webserver.request;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet manages a {@link RequestHandler} method
 *
 * @author Felix Vogel
 */
@SuppressWarnings("serial")
public class MethodExecServlet extends HttpServlet {

	private final MethodContainer[] delete, get, head, options, post, put;

	public MethodExecServlet(final Map<RequestMethod, List<MethodContainer>> sorted) {
		this.delete  = sorted.get(RequestMethod.DELETE).toArray(new MethodContainer[sorted.get(RequestMethod.DELETE).size()]);
		this.get     = sorted.get(RequestMethod.GET).toArray(new MethodContainer[sorted.get(RequestMethod.GET).size()]);
		this.head    = sorted.get(RequestMethod.HEAD).toArray(new MethodContainer[sorted.get(RequestMethod.HEAD).size()]);
		this.options = sorted.get(RequestMethod.OPTION).toArray(new MethodContainer[sorted.get(RequestMethod.OPTION).size()]);
		this.post    = sorted.get(RequestMethod.POST).toArray(new MethodContainer[sorted.get(RequestMethod.POST).size()]);
		this.put     = sorted.get(RequestMethod.PUT).toArray(new MethodContainer[sorted.get(RequestMethod.PUT).size()]);
	}

	public MethodExecServlet(final MethodContainer[] delete, final MethodContainer[] get, final MethodContainer[] head, final MethodContainer[] options, final MethodContainer[] post, final MethodContainer[] put) {
		this.delete  = delete;
		this.get     = get;
		this.head    = head;
		this.options = options;
		this.post    = post;
		this.put     = put;
	}

	@Override
	protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		if(delete != null && delete.length > 0) {
			for(final MethodContainer container : delete) {
				container.call(new RequestImpl(req, resp));
			}
		}
	}

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		if(get != null && get.length > 0) {
			for(final MethodContainer container : get) {
				container.call(new RequestImpl(req, resp));
			}
		}
	}

	@Override
	protected void doHead(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		if(head != null && head.length > 0) {
			for(final MethodContainer container : head) {
				container.call(new RequestImpl(req, resp));
			}
		}
	}

	@Override
	protected void doOptions(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		if(options != null && options.length > 0) {
			for(final MethodContainer container : options) {
				container.call(new RequestImpl(req, resp));
			}
		}
	}

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		if(post != null && post.length > 0) {
			for(final MethodContainer container : post) {
				container.call(new RequestImpl(req, resp));
			}
		}
	}

	@Override
	protected void doPut(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		if(put != null && put.length > 0) {
			for(final MethodContainer container : put) {
				container.call(new RequestImpl(req, resp));
			}
		}
	}

	@Override
	protected void doTrace(final HttpServletRequest arg0, final HttpServletResponse arg1) throws ServletException, IOException {
		arg1.setStatus(403);
	}

}

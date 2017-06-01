package com.octopusthu.ejw.security.web.api.component;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;

/**
 * 
 * @author zy
 * 
 */
public abstract class AbstractApiSecurityInterceptor implements Filter {
	public static final String PROCESSED = "AbstractApiSecurityInterceptor_Processed";
	public static final String ACCESS_GRANTED = "AbstractApiSecurityInterceptor_Access_Granted";
	public static final String ACCESS_DENIED_REASON = "AbstractApiSecurityInterceptor_Access_Denied_Reason";

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		if (req.getAttribute(PROCESSED) != null || !shouldProcess(request)) {
			chain.doFilter(request, res);
			return;
		}
		req.setAttribute(PROCESSED, Boolean.TRUE);
		process(request, (HttpServletResponse) res, chain);
	}

	protected abstract boolean shouldProcess(HttpServletRequest req);

	protected abstract void process(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	protected void accessGranted(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		// API access granted
		req.setAttribute(ACCESS_GRANTED, Boolean.TRUE);
		chain.doFilter(req, res);
		return;
	}

	protected void accessDenied(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Class<? extends AbstractApiSecurityInterceptor> clazz, String msg) {
		req.setAttribute(ACCESS_DENIED_REASON, msg);
		throw new AccessDeniedException("Access denied by " + clazz.getName() + ". Reason: " + msg);
	}
}

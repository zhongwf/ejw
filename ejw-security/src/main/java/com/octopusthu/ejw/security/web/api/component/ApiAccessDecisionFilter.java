package com.octopusthu.ejw.security.web.api.component;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.GenericFilterBean;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public class ApiAccessDecisionFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if (req.getAttribute(AbstractApiSecurityInterceptor.PROCESSED) != null) {
			if (req.getAttribute(AbstractApiSecurityInterceptor.ACCESS_GRANTED) != null) {
				chain.doFilter(req, res);
			} else {
				accessDenied(req, res, chain,
						(String) req.getAttribute(AbstractApiSecurityInterceptor.ACCESS_DENIED_REASON));
			}

		} else {
			accessDenied(req, res, chain, "Request not processed by any interceptor!");
		}
	}

	protected void accessDenied(ServletRequest req, ServletResponse res, FilterChain chain, String msg)
			throws IOException, ServletException {
		throw new AccessDeniedException(msg);
	}
}

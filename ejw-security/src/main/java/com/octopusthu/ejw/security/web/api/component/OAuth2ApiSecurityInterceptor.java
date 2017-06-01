package com.octopusthu.ejw.security.web.api.component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public class OAuth2ApiSecurityInterceptor extends AbstractApiSecurityInterceptor {

	@Override
	protected boolean shouldProcess(HttpServletRequest req) {
		return false;
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
	}
}

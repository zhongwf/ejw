package com.octopusthu.ejw.security.component;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

/**
 * An enhancement to {@link SimpleUrlAuthenticationSuccessHandler}, allowing
 * redirection based on current user's {@code GrantedAuthority}.
 * <p>
 * Like {@link SimpleUrlAuthenticationSuccessHandler}, this class prioritizes
 * the use of {@code alwaysUseDefaultTargetUrl} and {@code targetUrlParameter},
 * too. It then tries to find a matching url by examining the
 * {@code authentication} object against the {@code authorityUrlMap}, before
 * finally falling back to use {@code defaultTargetUrl}.
 * 
 * @author zhangyu octopusthu@gmail.com
 */
public class AuthorityBasedSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	protected final Log log = LogFactory.getLog(this.getClass());

	protected Map<? extends GrantedAuthority, String> authorityUrlMap = Collections.emptyMap();

	public AuthorityBasedSimpleUrlAuthenticationSuccessHandler(String targetUrlParameter, String defaultTargetUrl,
			RedirectStrategy redirectStrategy, Map<? extends GrantedAuthority, String> authorityUrlMap) {
		super();
		super.setDefaultTargetUrl(defaultTargetUrl);
		super.setTargetUrlParameter(StringUtils.hasText(targetUrlParameter) ? targetUrlParameter : null);
		super.setRedirectStrategy(redirectStrategy);
		this.authorityUrlMap = (authorityUrlMap == null) ? this.authorityUrlMap : authorityUrlMap;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String targetUrlParameter = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl()
				|| (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}

		String targetUrl = findAuthorityBasedTargetUrl(request, authentication);
		if (targetUrl != null) {
			clearAuthenticationAttributes(request);
			getRedirectStrategy().sendRedirect(request, response, targetUrl);
			return;
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}

	protected String findAuthorityBasedTargetUrl(HttpServletRequest request, Authentication authentication) {
		for (Map.Entry<? extends GrantedAuthority, String> entry : authorityUrlMap.entrySet()) {
			if (authentication.getAuthorities().contains(entry.getKey())) {
				return entry.getValue();
			}
		}
		return null;
	}

}

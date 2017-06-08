package com.octopusthu.ejw.security.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public class SecurityUtils {
	protected static final Log log = LogFactory.getLog(SecurityUtils.class);

	/**
	 * Make OrRequestMatcher of AntPathRequestMatchers
	 * 
	 */
	public static OrRequestMatcher makeOrRequestMatcher(@NotEmpty String[] patterns) {
		List<RequestMatcher> requestMatchers = new ArrayList<RequestMatcher>(patterns.length);
		for (String ignoredUrl : patterns) {
			requestMatchers.add(new AntPathRequestMatcher(ignoredUrl));
		}
		return new OrRequestMatcher(requestMatchers);
	}

	public static OrRequestMatcher makeOrRequestMatcher(@NotEmpty List<String> patterns) {
		return makeOrRequestMatcher(patterns.toArray(new String[patterns.size()]));
	}

	public static String findAuthorityBasedTargetUrl(Map<? extends GrantedAuthority, String> authorityUrlMap,
			Authentication authentication) {
		try {
			for (Map.Entry<? extends GrantedAuthority, String> entry : authorityUrlMap.entrySet()) {
				if (authentication.getAuthorities().contains(entry.getKey())) {
					return entry.getValue();
				}
			}
		} catch (Exception e) {
			log.warn("Exception finding authority based target url!", e);
			return null;
		}
		return null;
	}
}

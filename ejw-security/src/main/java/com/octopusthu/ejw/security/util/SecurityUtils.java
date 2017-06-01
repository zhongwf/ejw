package com.octopusthu.ejw.security.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public class SecurityUtils {

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
}

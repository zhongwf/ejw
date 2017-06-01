package com.octopusthu.ejw.security.authorization.dflt.domain;

import java.util.Collections;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AuthorizedRequest {
	@Getter
	private RequestMatcher matcher;
	@Getter
	private Set<? extends ConfigAttribute> authorities = Collections.emptySet();

	public AuthorizedRequest(RequestMatcher matcher) {
		this.matcher = matcher;
	}
}

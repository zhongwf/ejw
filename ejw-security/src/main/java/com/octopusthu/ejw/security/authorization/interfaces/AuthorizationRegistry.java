/**
 * 
 */
package com.octopusthu.ejw.security.authorization.interfaces;

import java.io.Serializable;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public interface AuthorizationRegistry {
	public Map<? extends Serializable, ? extends GrantedAuthority> getAllAuthorities();

	public Map<? extends RequestMatcher, String> getRequestMap();

	public Map<? extends GrantedAuthority, String> getAuthorityUrlMap();
}

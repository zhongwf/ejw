package com.octopusthu.ejw.security.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public interface UserAuthoritiesService {
	Collection<? extends GrantedAuthority> loadUserAuthoritiesByUsername(String username);
}

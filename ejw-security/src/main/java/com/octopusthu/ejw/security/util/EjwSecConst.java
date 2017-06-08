package com.octopusthu.ejw.security.util;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class EjwSecConst {
	public static final String AUTHORITY_PREFIX_LOCAL_ROLE = "LOCAL_ROLE_";
	public static final String AUTHORITY_AUTHENTICATED = "authenticated-user";
	public static final SimpleGrantedAuthority AUTHORITY_AUTHENTICATED_USER = new SimpleGrantedAuthority(
			AUTHORITY_PREFIX_LOCAL_ROLE + AUTHORITY_AUTHENTICATED);
}

package com.octopusthu.ejw.security.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.octopusthu.ejw.exception.SecurityContextException;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public class SecurityContextUtils {
	protected static final Log log = LogFactory.getLog(SecurityContextUtils.class);

	public static Authentication getAuthentication() throws SecurityContextException {
		Authentication authentication;
		try {
			authentication = SecurityContextHolder.getContext().getAuthentication();
		} catch (Exception e) {
			throw new SecurityContextException("Exception retrieving Authentication.");
		}
		if (authentication == null) {
			throw new SecurityContextException("Exception retrieving Authentication.");
		}
		return authentication;
	}

	public static boolean isAuthenticated() {
		Authentication auth;
		try {
			auth = getAuthentication();
		} catch (SecurityContextException e) {
			return false;
		}
		return (auth != null && auth.isAuthenticated());
	}

	public static User getUserDetails(Authentication authentication) throws SecurityContextException {
		User user;
		try {
			user = (User) authentication.getPrincipal();
		} catch (Exception e) {
			throw new SecurityContextException("Exception retrieving UserDetails.");
		}
		if (user == null) {
			throw new SecurityContextException("Exception retrieving UserDetails.");
		}
		return user;
	}

	public static User getUserDetails() throws SecurityContextException {
		return getUserDetails(getAuthentication());
	}

	public static String getUserId() throws SecurityContextException {
		return getUserDetails().getUsername();
	}

	public static Collection<? extends GrantedAuthority> getUserAuthorites(Authentication authentication)
			throws SecurityContextException {
		return getUserDetails(authentication).getAuthorities();
	}

	public static Collection<? extends GrantedAuthority> getUserAuthorites() throws SecurityContextException {
		return getUserAuthorites(getAuthentication());
	}

	public static Set<String> getLocalRoles() throws AuthenticationException {
		return getUserAuthoritesByPrefix(EjwSecConst.AUTHORITY_PREFIX_LOCAL_ROLE, false);
	}

	public static Set<String> getUserAuthoritesByPrefix(String prefix, boolean withPrefix,
			Authentication authentication) throws SecurityContextException {
		Set<String> ret = new HashSet<String>();
		Collection<? extends GrantedAuthority> authorities = getUserAuthorites(authentication);
		if (authorities == null || authorities.isEmpty()) {
			return ret;
		}
		for (GrantedAuthority authority : authorities) {
			String strAuthority = authority.getAuthority();
			if (strAuthority.startsWith(prefix)) {
				ret.add(withPrefix ? strAuthority : strAuthority.substring(prefix.length()));
			}
		}
		return ret;
	}

	public static Set<String> getUserAuthoritesByPrefix(String prefix, boolean withPrefix)
			throws SecurityContextException {
		return getUserAuthoritesByPrefix(prefix, withPrefix, getAuthentication());
	}

}

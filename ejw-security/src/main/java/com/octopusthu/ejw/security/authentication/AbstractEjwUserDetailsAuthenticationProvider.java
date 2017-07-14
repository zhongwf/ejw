package com.octopusthu.ejw.security.authentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.octopusthu.ejw.security.util.EjwSecConst;

/**
 * This class makes several enhancements to
 * {@link org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider}:
 *
 * 1. Breaks the {@code retrieveUser} method into several fine-grained
 * sub-methods.
 * 
 * 2. User authorities are populated by {@link UserAuthoritiesService}s before
 * {@code retrieveUser} construct the {@link UserDetails} object.
 * 
 * 3. Null implementation of <@code additionalAuthenticationChecks> so that
 * subclasses do not have to override it.
 * 
 * @author zhangyu octopusthu@gmail.com
 */
public abstract class AbstractEjwUserDetailsAuthenticationProvider<U extends User, AR extends AuthenticationResult, UDS extends AbstractUserDetailsService<U, AR>>
		extends AbstractUserDetailsAuthenticationProvider {
	protected UDS userDetailsService;
	protected List<UserAuthoritiesService> userAuthoritiesServices;

	public AbstractEjwUserDetailsAuthenticationProvider(UDS userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public AbstractEjwUserDetailsAuthenticationProvider(UDS userDetailsService,
			UserAuthoritiesService... userAuthoritiesServices) {
		this(userDetailsService);
		if (ArrayUtils.isNotEmpty(userAuthoritiesServices)) {
			this.userAuthoritiesServices = Arrays.asList(userAuthoritiesServices);
		}
	}

	@Override
	protected U retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		AR authenticationResult = authenticateUser(username, authentication);
		if (!authenticationResult.isSuccessful()) {
			throw new BadCredentialsException("Unsuccessful authentication! username=" + username);
		}
		username = authenticationResult.getUsername();
		Collection<GrantedAuthority> authorities = loadUserAuthorities(username, authentication);
		return userDetailsService.loadUser(authentication, username, authorities, authenticationResult);
	}

	protected abstract AR authenticateUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException;

	protected Collection<GrantedAuthority> loadUserAuthorities(String username,
			UsernamePasswordAuthenticationToken authentication) {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.addAll(authentication.getAuthorities());
		authorities.add(EjwSecConst.AUTHORITY_AUTHENTICATED_USER);
		if (CollectionUtils.isNotEmpty(userAuthoritiesServices)) {
			userAuthoritiesServices.forEach(service -> {
				authorities.addAll(service.loadUserAuthoritiesByUsername(username));
			});
		}
		return authorities;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}
}

/**
 * 
 */
package com.octopusthu.ejw.security.authentication;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public abstract class AbstractUserDetailsService<U extends User, AR extends Object> implements UserDetailsService {
	protected final Log log = LogFactory.getLog(this.getClass());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		throw new UsernameNotFoundException("'loadUserByUsername' not supported.");
	}

	public abstract U loadUser(final Authentication authentication, final String username,
			Collection<GrantedAuthority> authorities, final AR authenticationResult) throws UsernameNotFoundException;

}

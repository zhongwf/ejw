package com.octopusthu.ejw.security.authentication;

import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import lombok.Setter;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public class SimpleUserAuthoritiesService implements UserAuthoritiesService {
	private Map<String, Collection<? extends GrantedAuthority>> map = new Hashtable<String, Collection<? extends GrantedAuthority>>();
	@Setter
	private String usersDelimiter = ";";
	@Setter
	private String userAuthorityDelimiter = ":";
	@Setter
	private String authoritiesDelimiter = ",";

	public SimpleUserAuthoritiesService(String data) {
		try {
			for (String item : data.split(usersDelimiter)) {
				String[] delimitedItem = item.split(userAuthorityDelimiter);
				String user = delimitedItem[0];
				String strAuthorities = delimitedItem[1];
				Collection<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
				map.put(user, authorities);
				for (String strAuthority : strAuthorities.split(authoritiesDelimiter)) {
					authorities.add(new SimpleGrantedAuthority(strAuthority));
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Malformed user authorities data!");
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> loadUserAuthoritiesByUsername(String username) {
		Collection<? extends GrantedAuthority> authorities = map.get(username);
		return CollectionUtils.isEmpty(authorities) ? AuthorityUtils.NO_AUTHORITIES : authorities;
	}

}

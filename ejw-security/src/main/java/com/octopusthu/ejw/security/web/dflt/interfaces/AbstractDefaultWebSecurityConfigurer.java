package com.octopusthu.ejw.security.web.dflt.interfaces;

import javax.servlet.Filter;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import com.octopusthu.ejw.security.web.dflt.config.DefaultWebSecurityConfig;

/**
 * Marker class to be extended by subclasses for customizations of
 * {@link DefaultWebSecurityConfig}
 * 
 * An implementing class should be exposed as a spring bean in order to be
 * discovered, and preferably with an {@link Order}.
 * 
 * @author zhangyu octopusthu@gmail.com
 *
 */
public abstract class AbstractDefaultWebSecurityConfigurer {
	public void configureAuthenticationProviders(AuthenticationManagerBuilderDelegate auth) {
	};

	public void configureFilters(HttpSecurityFiltersDelegate http) {
	};

	/* Delegates */

	public static class AuthenticationManagerBuilderDelegate {
		private AuthenticationManagerBuilder auth;

		public AuthenticationManagerBuilderDelegate(AuthenticationManagerBuilder auth) {
			this.auth = auth;
		}

		public void authenticationProvider(AuthenticationProvider authenticationProvider) {
			auth.authenticationProvider(authenticationProvider);
		}
	}

	public static class HttpSecurityFiltersDelegate {
		private HttpSecurity httpSecurity;

		public HttpSecurityFiltersDelegate(HttpSecurity httpSecurity) {
			this.httpSecurity = httpSecurity;
		}

		public void addFilterAfter(Filter filter, Class<? extends Filter> afterFilter) {
			httpSecurity.addFilterAfter(filter, afterFilter);
		}

		public void addFilterBefore(Filter filter, Class<? extends Filter> beforeFilter) {
			httpSecurity.addFilterBefore(filter, beforeFilter);
		}

		public void addFilterAt(Filter filter, Class<? extends Filter> atFilter) {
			httpSecurity.addFilterAt(filter, atFilter);
		}
	}
}

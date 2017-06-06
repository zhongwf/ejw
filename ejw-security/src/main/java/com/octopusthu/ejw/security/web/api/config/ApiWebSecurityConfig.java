package com.octopusthu.ejw.security.web.api.config;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import com.octopusthu.ejw.security.component.SecurityProps;
import com.octopusthu.ejw.security.web.api.component.ApiAccessDecisionFilter;
import com.octopusthu.ejw.security.web.api.component.LocalRegistrySimpleApiSecurityAuthenticationProvider;
import com.octopusthu.ejw.security.web.api.component.OAuth2ApiSecurityInterceptor;
import com.octopusthu.ejw.security.web.api.component.SimpleApiSecurityInterceptor;
import com.octopusthu.ejw.security.web.api.interfaces.SimpleApiSecurityAuthenticationProvider;

@ConditionalOnProperty("ejw.security.api.enabled")
@Configuration
@EnableWebSecurity
@Order(Ordered.LOWEST_PRECEDENCE - 30)
public class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Inject
	private SecurityProps props;

	@Autowired(required = false)
	SimpleApiSecurityAuthenticationProvider simpleApiSecurityAuthenticationProvider;

	public ApiWebSecurityConfig() {
		super(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		SecurityProps.Api apiProps = props.getApi();

		http.antMatcher(apiProps.getAntPattern());
		http.securityContext();
		http.exceptionHandling();

		SimpleApiSecurityAuthenticationProvider provider = simpleApiSecurityAuthenticationProvider;
		if (provider == null) {
			provider = new LocalRegistrySimpleApiSecurityAuthenticationProvider(
					props.getApi().getSimple().getAppRegistry());
		}
		SimpleApiSecurityInterceptor simpleApiSecurityInterceptor = new SimpleApiSecurityInterceptor(props, provider);
		http.addFilterAfter(new OAuth2ApiSecurityInterceptor(), ExceptionTranslationFilter.class);
		http.addFilterAfter(simpleApiSecurityInterceptor, OAuth2ApiSecurityInterceptor.class);
		http.addFilterAfter(new ApiAccessDecisionFilter(), SimpleApiSecurityInterceptor.class);
	}
}

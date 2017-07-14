package com.octopusthu.ejw.security.web.dflt.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.authentication.AuthenticationManagerBeanDefinitionParser;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.octopusthu.ejw.security.SecurityProps;
import com.octopusthu.ejw.security.authorization.interfaces.AuthorizationRegistry;
import com.octopusthu.ejw.security.web.dflt.interfaces.AbstractDefaultWebSecurityConfigurer;
import com.octopusthu.ejw.security.web.dflt.interfaces.AbstractDefaultWebSecurityConfigurer.AuthenticationManagerBuilderDelegate;
import com.octopusthu.ejw.security.web.dflt.interfaces.AbstractDefaultWebSecurityConfigurer.HttpSecurityFiltersDelegate;

@ConditionalOnProperty("ejw.security.dflt.enabled")
@Configuration
@EnableWebSecurity
@Order(Ordered.LOWEST_PRECEDENCE - 2)
public class DefaultWebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final Log log = LogFactory.getLog(getClass());

	@Inject
	private SecurityProps props;
	@Inject
	AuthorizationRegistry authorizationRegistry;
	@Inject
	private PortMapper portMapper;
	@Inject
	private PortResolver portResolver;
	@Inject
	private RedirectStrategy redirectStrategy;
	@Inject
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Inject
	private AuthenticationFailureHandler authenticationFailureHandler;

	@Autowired(required = false)
	private List<AbstractDefaultWebSecurityConfigurer> configurers;

	@PostConstruct
	private void init() {
		configurers = (configurers == null) ? Collections.emptyList() : configurers;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		AuthenticationManagerBuilderDelegate delegate = new AuthenticationManagerBuilderDelegate(auth);
		configurers.forEach(c -> {
			c.configureAuthenticationProviders(delegate);
		});
		if (!auth.isConfigured()) {
			auth.authenticationProvider(new AuthenticationManagerBeanDefinitionParser.NullAuthenticationProvider());
			log.warn("No authentication providers found!");
		}
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(props.getDflt().getIgnoring().getAntPatterns());
		web.expressionHandler(defaultWebSecurityExpressionHandler());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (props.getDflt().getAntPattern() != null) {
			http.antMatcher(props.getDflt().getAntPattern());
		}

		http.csrf().disable();

		http.portMapper().portMapper(portMapper);
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
				.accessDeniedPage(props.getDflt().getAccessDeniedPage());

		SecurityProps.Default.Logout logoutProps = props.getDflt().getLogout();
		SimpleUrlLogoutSuccessHandler logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
		logoutSuccessHandler.setDefaultTargetUrl(logoutProps.getLogoutSuccessUrl());
		logoutSuccessHandler.setRedirectStrategy(redirectStrategy);
		http.logout().logoutUrl(logoutProps.getUrl()).logoutSuccessHandler(logoutSuccessHandler);

		SecurityProps.Default.Login loginProps = props.getDflt().getLogin();
		http.formLogin().loginPage(props.getDflt().getLoginFormUrl())
				.usernameParameter(loginProps.getUsernameParameter())
				.passwordParameter(loginProps.getPasswordParameter())
				.loginProcessingUrl(loginProps.getLoginProcessingUrl()).successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler);

		/* allow for local customizations */
		HttpSecurityFiltersDelegate delegate = new HttpSecurityFiltersDelegate(http);
		configurers.forEach(c -> {
			c.configureFilters(delegate);
		});

		/* authorizations */
		for (Map.Entry<? extends RequestMatcher, String> entry : authorizationRegistry.getRequestMap().entrySet()) {
			http.authorizeRequests().requestMatchers(entry.getKey()).access(entry.getValue());
		}

		http.authorizeRequests().antMatchers(props.getDflt().getPermitPatterns()).permitAll();

		if (props.getDflt().isRejectPublicInvocations()) {
			http.authorizeRequests().anyRequest().denyAll();
		} else {
			http.authorizeRequests().anyRequest().permitAll();
		}

	}

	private AuthenticationEntryPoint authenticationEntryPoint() throws Exception {
		LoginUrlAuthenticationEntryPoint entry = new LoginUrlAuthenticationEntryPoint(
				props.getDflt().getLoginFormUrl());
		entry.setForceHttps(props.isForceHttps());
		entry.setPortMapper(portMapper);
		entry.setPortResolver(portResolver);
		entry.afterPropertiesSet();
		return entry;
	}

	private DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler() throws Exception {
		DefaultWebSecurityExpressionHandler ret = new DefaultWebSecurityExpressionHandler();
		ret.setDefaultRolePrefix(props.getRolePrefix());
		return ret;
	}
}

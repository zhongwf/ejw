package com.octopusthu.ejw.security.config;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.octopusthu.ejw.security.authorization.interfaces.AuthorizationRegistry;
import com.octopusthu.ejw.security.component.AuthorityBasedSavedRequestAwareAuthenticationSuccessHandler;
import com.octopusthu.ejw.security.component.HttpsAwareRedirectStrategy;
import com.octopusthu.ejw.security.component.SecurityProps;

/**
 * @author zhangyu octopusthu@gmail.com
 */
@Configuration
@PropertySource(value = "classpath:ejw-security.properties", encoding = "UTF-8")
public class RootSecurityConfig {

	@Inject
	AuthorizationRegistry authorizationRegistry;

	@Bean
	@ConfigurationProperties("ejw.security")
	public SecurityProps securityProps() {
		return new SecurityProps();
	}

	/**
	 * Set default role prefix to blank. Proper role prefixes will be set by the
	 * framework and other security-related classes.
	 */
	@Bean
	GrantedAuthorityDefaults grantedAuthorityDefaults() {
		return new GrantedAuthorityDefaults(securityProps().getRolePrefix());
	}

	@Bean
	public RedirectStrategy redirectStrategy() {
		HttpsAwareRedirectStrategy ret = new HttpsAwareRedirectStrategy();
		ret.setForceHttps(securityProps().isForceHttps());
		ret.setPortResolver(portResolver());
		ret.setPortMapper(portMapper());
		return ret;
	}

	@Bean
	public PortResolver portResolver() {
		PortResolverImpl portResolver = new PortResolverImpl();
		portResolver.setPortMapper(portMapper());
		return portResolver;
	}

	@Bean
	public PortMapper portMapper() {
		PortMapperImpl portMapper = new PortMapperImpl();
		Map<String, String> httpsPortMappings = new HashMap<String, String>();
		for (String portMapping : securityProps().getPortMappings()) {
			String[] mapping = portMapping.split(":");
			httpsPortMappings.put(mapping[0], mapping[1]);
		}
		portMapper.setPortMappings(httpsPortMappings);
		return portMapper;
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		SecurityProps.Authentication authenticationProps = securityProps().getAuthentication();
		AuthorityBasedSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler = new AuthorityBasedSavedRequestAwareAuthenticationSuccessHandler(
				authenticationProps.getTargetUrlParameter(), authenticationProps.getDefaultTargetUrl(),
				redirectStrategy(), authorizationRegistry.getAuthorityUrlMap());
		authenticationSuccessHandler.setRedirectStrategy(redirectStrategy());
		return authenticationSuccessHandler;
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		SimpleUrlAuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler(
				securityProps().getAuthentication().getFailureUrl());
		authenticationFailureHandler.setRedirectStrategy(redirectStrategy());
		return authenticationFailureHandler;
	}

}
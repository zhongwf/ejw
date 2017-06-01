package com.octopusthu.ejw.security.authorization.dflt.config;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.octopusthu.ejw.security.authorization.dflt.DefaultAuthorizationRegistry;
import com.octopusthu.ejw.security.authorization.dflt.interfaces.AbstractDefaultAuthorizationConfigurer;
import com.octopusthu.ejw.security.authorization.interfaces.AuthorizationRegistry;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty("ejw.security.authorization.dflt.enabled")
@ConditionalOnMissingBean(AuthorizationRegistry.class)
@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 5)
public class DefaultAuthorizationConfig {

	@Autowired(required = false)
	private List<AbstractDefaultAuthorizationConfigurer> configurers;

	@PostConstruct
	private void init() {
		configurers = (configurers == null) ? Collections.emptyList() : configurers;
	}

	@Bean
	public DefaultAuthorizationRegistry defaultAuthorizationRegistry() {
		DefaultAuthorizationRegistry registry = new DefaultAuthorizationRegistry();
		configurers.forEach(c -> {
			c.configure(registry);
		});
		return registry;
	}
}

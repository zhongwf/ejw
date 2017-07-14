package com.octopusthu.ejw.components.admin.config;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.octopusthu.ejw.security.util.EjwSecConst;
import com.octopusthu.ejw.security.util.SecurityUtils;

import lombok.Getter;
import lombok.Setter;

@ConditionalOnProperty(AdminConfig.CONDITION)
@Configuration
@PropertySource(value = { "classpath:ejw-admin.properties" }, encoding = "UTF-8")
public class AdminConfig {

	protected static final String CONDITION = "ejw.components.admin.enabled";

	@Bean("adminProps")
	AdminProps props() {
		return new AdminProps();
	}

	@ConfigurationProperties("ejw.components.admin")
	@Getter
	@Setter
	public static class AdminProps {
	}

	public AdminSecurityConfig adminSecurityConfig() {
		return new AdminSecurityConfig();
	}

	@ConditionalOnProperty(AdminConfig.CONDITION)
	@Configuration
	@EnableWebSecurity
	@Order(Ordered.LOWEST_PRECEDENCE - 50)
	public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

		@Bean("adminSecurityProps")
		public AdminSecurityProps props() {
			return new AdminSecurityProps();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher(props().getPattern());

			String[] roles = props().getRoles();
			if (ArrayUtils.isNotEmpty(roles)) {
				http.authorizeRequests().anyRequest().access(SecurityUtils.rolesToAccessExpression(roles));
			}
		}

		@ConfigurationProperties("ejw.components.admin.security")
		@Getter
		@Setter
		public static class AdminSecurityProps {
			private String pattern = "/admin/**";
			private String[] roles = { EjwSecConst.AUTHORITY_PREFIX_LOCAL_ROLE + "ADMIN" };
		}
	}
}
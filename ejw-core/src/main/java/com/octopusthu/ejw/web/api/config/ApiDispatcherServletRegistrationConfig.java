package com.octopusthu.ejw.web.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.octopusthu.ejw.web.AnnotationConfigDispatcherServletRegistrationBean;
import com.octopusthu.ejw.web.api.ApiWebApplicationContextConfig;

@ConditionalOnProperty("ejw.web.api.enabled")
@Configuration
public class ApiDispatcherServletRegistrationConfig {

	@Bean
	public ServletRegistrationBean apiDispatcherServletRegistrationBean(
			@Value("${ejw.web.api.servlet.name:spring-api}") String servletName,
			@Value("${ejw.web.api.servlet.urlMappings:/a/*}") String[] urlMappings,
			@Value("${ejw.web.api.servlet.loadOnStartup:3}") int loadOnStartup) {
		ServletRegistrationBean bean = new AnnotationConfigDispatcherServletRegistrationBean(
				ApiWebApplicationContextConfig.class);
		bean.setName(servletName);
		bean.addUrlMappings(urlMappings);
		bean.setLoadOnStartup(loadOnStartup);
		return bean;
	}
}

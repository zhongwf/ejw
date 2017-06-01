package com.octopusthu.ejw.web.back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.octopusthu.ejw.web.AnnotationConfigDispatcherServletRegistrationBean;
import com.octopusthu.ejw.web.back.BackWebApplicationContextConfig;

@ConditionalOnProperty("ejw.web.back.enabled")
@Configuration
public class BackDispatcherServletRegistrationConfig {

	@Bean
	public ServletRegistrationBean backDispatcherServletRegistrationBean(
			@Value("${ejw.web.back.servlet.name:spring-back}") String servletName,
			@Value("${ejw.web.back.servlet.urlMappings:/b/*}") String[] urlMappings,
			@Value("${ejw.web.back.servlet.loadOnStartup:2}") int loadOnStartup) {
		ServletRegistrationBean bean = new AnnotationConfigDispatcherServletRegistrationBean(
				BackWebApplicationContextConfig.class);
		bean.setName(servletName);
		bean.addUrlMappings(urlMappings);
		bean.setLoadOnStartup(loadOnStartup);
		return bean;
	}
}

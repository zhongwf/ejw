package com.octopusthu.ejw.web.dflt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.octopusthu.ejw.web.AnnotationConfigDispatcherServletRegistrationBean;
import com.octopusthu.ejw.web.dflt.DefaultWebApplicationContextConfig;

@ConditionalOnProperty("ejw.web.default.enabled")
@Configuration
public class DefaultDispatcherServletRegistrationConfig {

	@Bean
	public ServletRegistrationBean defaultDispatcherServletRegistrationBean(
			@Value("${ejw.web.default.servlet.name:spring-default}") String servletName,
			@Value("${ejw.web.default.servlet.urlMappings:/}") String[] urlMappings,
			@Value("${ejw.web.default.servlet.loadOnStartup:1}") int loadOnStartup) {
		ServletRegistrationBean bean = new AnnotationConfigDispatcherServletRegistrationBean(
				DefaultWebApplicationContextConfig.class);
		bean.setName(servletName);
		bean.addUrlMappings(urlMappings);
		bean.setLoadOnStartup(loadOnStartup);
		return bean;
	}
}

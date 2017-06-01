package com.octopusthu.ejw.web.front.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.octopusthu.ejw.web.AnnotationConfigDispatcherServletRegistrationBean;
import com.octopusthu.ejw.web.front.FrontWebApplicationContextConfig;

@ConditionalOnProperty("ejw.web.front.enabled")
@Configuration
public class FrontDispatcherServletRegistrationConfig {

	@Bean
	public ServletRegistrationBean frontDispatcherServletRegistrationBean(
			@Value("${ejw.web.front.servlet.name:spring-front}") String servletName,
			@Value("${ejw.web.front.servlet.urlMappings:/f/*}") String[] urlMappings,
			@Value("${ejw.web.front.servlet.loadOnStartup:2}") int loadOnStartup) {
		ServletRegistrationBean bean = new AnnotationConfigDispatcherServletRegistrationBean(
				FrontWebApplicationContextConfig.class);
		bean.setName(servletName);
		bean.addUrlMappings(urlMappings);
		bean.setLoadOnStartup(loadOnStartup);
		return bean;
	}
}

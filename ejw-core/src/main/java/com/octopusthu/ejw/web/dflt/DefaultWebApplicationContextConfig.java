package com.octopusthu.ejw.web.dflt;

import javax.inject.Inject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.octopusthu.ejw.web.dflt.component.EjwWebDfltProps;

@Configuration
@EnableWebMvc
@ComponentScan({ "${ejw.web.default.basePackages}" })
public class DefaultWebApplicationContextConfig extends WebMvcConfigurerAdapter {
	@Inject
	EjwWebDfltProps props;

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		for (String path : props.getStaticResourcesPaths()) {
			path = path.endsWith("/") ? path : path + "/";
			registry.addResourceHandler(path + "**").addResourceLocations(path);
		}
	}
}

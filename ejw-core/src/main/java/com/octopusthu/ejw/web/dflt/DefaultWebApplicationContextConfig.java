package com.octopusthu.ejw.web.dflt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan({ "${ejw.web.default.basePackages}" })
public class DefaultWebApplicationContextConfig extends WebMvcConfigurerAdapter {

	@Value("${ejw.web.default.staticResourcesPaths:/res/}")
	private String[] staticResourcesPath;

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		for (String path : staticResourcesPath) {
			path = path.endsWith("/") ? path : path + "/";
			registry.addResourceHandler(path + "**").addResourceLocations(path);
		}
	}
}

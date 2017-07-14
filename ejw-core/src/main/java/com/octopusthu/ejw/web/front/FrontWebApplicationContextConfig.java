package com.octopusthu.ejw.web.front;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.octopusthu.ejw.web.front.component.EjwWebFrontProps;

@Configuration
@EnableWebMvc
@ComponentScan({ "${ejw.web.front.basePackages}" })
public class FrontWebApplicationContextConfig extends WebMvcConfigurerAdapter {
	@Inject
	EjwWebFrontProps props;

	@Autowired(required = false)
	protected List<HandlerInterceptor> handlerInterceptors;

	/* TODO: Finer control by properties */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (CollectionUtils.isNotEmpty(handlerInterceptors)) {
			for (HandlerInterceptor interceptor : handlerInterceptors) {
				registry.addInterceptor(interceptor);
			}
		}
	}

}

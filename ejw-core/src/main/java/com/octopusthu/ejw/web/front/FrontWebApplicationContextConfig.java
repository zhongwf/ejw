package com.octopusthu.ejw.web.front;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.octopusthu.ejw.web.front.component.EjwWebFrontProps;

@Configuration
@EnableWebMvc
@ComponentScan({ "${ejw.web.front.basePackages}" })
public class FrontWebApplicationContextConfig extends WebMvcConfigurerAdapter {
	@Inject
	EjwWebFrontProps props;

	@Autowired(required = false)
	protected List<HandlerInterceptor> handlerInterceptors;

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		EjwWebFrontProps.InternalResourceViewResolver resolverProps = props.getInternalResourceViewResolver();
		if (resolverProps.isEnabled()) {
			InternalResourceViewResolver resolver = new InternalResourceViewResolver();
			resolver.setPrefix(resolverProps.getPrefix());
			resolver.setSuffix(resolverProps.getSuffix());
			resolver.setViewClass(JstlView.class);
			registry.viewResolver(resolver);
		}
	}

	@ConditionalOnProperty("ejw.web.front.jspViewResolver.enabled")
	// @Bean
	public ViewResolver jspViewResolver(@Value("${ejw.web.front.jspViewResolver.prefix:/WEB-INF/view}") String prefix,
			@Value("${ejw.web.front.jspViewResolver.suffix:.jsp}") String suffix) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix(prefix);
		viewResolver.setSuffix(suffix);
		return viewResolver;
	}

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

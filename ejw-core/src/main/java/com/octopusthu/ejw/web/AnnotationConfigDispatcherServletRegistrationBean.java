package com.octopusthu.ejw.web;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnotationConfigDispatcherServletRegistrationBean extends ServletRegistrationBean {

	public AnnotationConfigDispatcherServletRegistrationBean(Class<?> configClass) {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(configClass);
		super.setServlet(new DispatcherServlet(applicationContext));
	}
}

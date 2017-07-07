package com.octopusthu.ejw.web.front.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("ejw.web.front")
@Getter
@Setter
public class EjwWebFrontProps {
	private Servlet servlet = new Servlet();
	private InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();

	@Getter
	@Setter
	public static class Servlet {
		private String name = "spring-front";
		private String urlMappings = "/f/*";
		private int loadOnStartup = 2;
	}

	@Getter
	@Setter
	public static class InternalResourceViewResolver {
		private boolean enabled = false;
		private String prefix = "/WEB-INF/view";
		private String suffix = ".jsp";
	}

}

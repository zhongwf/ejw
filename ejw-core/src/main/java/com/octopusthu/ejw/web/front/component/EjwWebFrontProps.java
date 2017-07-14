package com.octopusthu.ejw.web.front.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("ejw.web.front")
@Getter
@Setter
public class EjwWebFrontProps {
	private Servlet servlet = new Servlet();

	@Getter
	@Setter
	public static class Servlet {
		private String name = "spring-front";
		private String urlMappings = "/f/*";
		private int loadOnStartup = 2;
	}

}

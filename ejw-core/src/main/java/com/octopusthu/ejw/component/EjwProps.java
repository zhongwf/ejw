package com.octopusthu.ejw.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("ejw")
@Getter
@Setter
public class EjwProps {
	private String appName;
	private Filters filters = new Filters();

	@Getter
	@Setter
	public static class Filters {
		private CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		private ShallowEtagHeaderFilter shallowEtagHeaderFilter = new ShallowEtagHeaderFilter();

		@Getter
		@Setter
		public static class CharacterEncodingFilter {
			private String name = "characterEncodingFilter";
			private String encoding = "UTF-8";
			private String[] urlPatterns = {};
			private String[] servletNames = {};
		}

		@Getter
		@Setter
		public static class ShallowEtagHeaderFilter {
			private String name = "shallowEtagHeaderFilter";
			private String[] urlPatterns = {};
			private String[] servletNames = {};
		}

	}

}

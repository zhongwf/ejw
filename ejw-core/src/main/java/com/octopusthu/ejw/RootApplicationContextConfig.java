package com.octopusthu.ejw;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.octopusthu.ejw.component.ExposedResourceBundleMessageSource;

@Configuration
@ComponentScan({ "${ejw.basePackages}" })
@PropertySource(value = { "classpath:ejw-core.properties" }, encoding = "UTF-8")
@EnableConfigurationProperties
public class RootApplicationContextConfig {

	// TODO: Caches

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySource() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public ExposedResourceBundleMessageSource messageSource(
			@Value("${ejw.messageSource.basenames:messages,messages_ejw_core}") String[] basenames,
			@Value("${ejw.messageSource.defaultEncoding:UTF-8}") String defaultEncoding,
			@Value("${ejw.messageSource.useCodeAsDefaultMessage:false}") boolean useCodeAsDefaultMessage) {
		ExposedResourceBundleMessageSource source = new ExposedResourceBundleMessageSource();
		source.setBasenames(basenames);
		source.setDefaultEncoding(defaultEncoding);
		source.setUseCodeAsDefaultMessage(useCodeAsDefaultMessage);
		return source;
	}

}

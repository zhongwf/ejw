package com.octopusthu.ejw.components.i18n.config;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.octopusthu.ejw.component.ExposedResourceBundleMessageSource;
import com.octopusthu.ejw.components.i18n.ExposedResourceBundleMessageSourceService;
import com.octopusthu.ejw.components.i18n.LocaleService;

@ConditionalOnProperty("ejw.components.i18n.enabled")
@Configuration("ejw-component-i18n-config")
public class I18nConfig {

	@Inject
	ExposedResourceBundleMessageSource messages;

	@Bean
	public LocaleService localeService() {
		return new LocaleService();
	}

	@Bean
	public ExposedResourceBundleMessageSourceService exposedResourceBundleMessageSourceService() {
		return new ExposedResourceBundleMessageSourceService(messages, localeService());
	}

	@Bean
	@Order(5)
	public LocaleChangeInterceptor localeChangeInterceptor(
			@Value("${ejw.components.i18n.localeChangeInterceptor.paramName:locale}") String paramName,
			@Value("${ejw.components.i18n.localeChangeInterceptor.httpMethods:POST}") String[] httpMethods) {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName(paramName);
		interceptor.setHttpMethods(httpMethods);
		return interceptor;
	}

	@ConditionalOnProperty(value = "ejw.components.i18n.localeResolver", havingValue = "cookieLocaleResolver")
	@Bean
	public CookieLocaleResolver localeResolver(
			@Value("${ejw.components.i18n.defaultLocale:en_US}") String defaultLocale,
			@Value("${ejw.components.i18n.cookieLocaleResolver.cookieName:LOCALE}") String cookieName,
			@Value("${ejw.components.i18n.cookieLocaleResolver.cookieMaxAge:5184000}") int cookieMaxAge) {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(StringUtils.parseLocaleString(defaultLocale));
		resolver.setCookieName(cookieName);
		resolver.setCookieMaxAge(cookieMaxAge);
		return resolver;
	}
}

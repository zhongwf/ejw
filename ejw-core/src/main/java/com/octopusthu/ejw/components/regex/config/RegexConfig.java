package com.octopusthu.ejw.components.regex.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.octopusthu.ejw.components.regex.MobileRegexMatcher;

@ConditionalOnProperty("ejw.components.regex.mobile")
@Configuration("ejw-component-regex-config")
public class RegexConfig {

	@Bean
	public static MobileRegexMatcher mobileRegexMatcher() {
		return new MobileRegexMatcher();
	}
}

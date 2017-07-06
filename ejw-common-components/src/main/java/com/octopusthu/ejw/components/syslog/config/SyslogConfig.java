package com.octopusthu.ejw.components.syslog.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.octopusthu.ejw.components.syslog.SyslogEntryTemplates;

@ConditionalOnProperty("ejw.components.syslog.enabled")
@Configuration("ejw-component-syslog-config")
public class SyslogConfig {

	@Bean
	public SyslogEntryTemplates syslogEntryTemplates() {
		return new SyslogEntryTemplates();
	}
}

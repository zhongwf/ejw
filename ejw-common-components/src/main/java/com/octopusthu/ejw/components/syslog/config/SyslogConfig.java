package com.octopusthu.ejw.components.syslog.config;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.octopusthu.ejw.components.syslog.Syslog;
import com.octopusthu.ejw.components.syslog.SyslogService;

@ConditionalOnProperty("ejw.components.syslog.enabled")
@Configuration("ejw-component-syslog-config")
public class SyslogConfig {
	@Inject
	private Syslog syslog;

	@Bean
	public SyslogService syslogService() {
		return new SyslogService(syslog);
	}
}

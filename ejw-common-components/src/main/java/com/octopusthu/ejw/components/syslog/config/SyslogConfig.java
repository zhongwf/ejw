package com.octopusthu.ejw.components.syslog.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty("ejw.components.syslog.enabled")
@Configuration("ejw-component-syslog-config")
public class SyslogConfig {
}

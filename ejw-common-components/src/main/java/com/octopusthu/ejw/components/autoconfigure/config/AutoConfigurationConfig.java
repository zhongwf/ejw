package com.octopusthu.ejw.components.autoconfigure.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty("ejw.components.autoconfigure.enabled")
@Configuration
@EnableAutoConfiguration(exclude = { ServerPropertiesAutoConfiguration.class, WebSocketAutoConfiguration.class,
		EmbeddedServletContainerAutoConfiguration.class, JdbcTemplateAutoConfiguration.class })
public class AutoConfigurationConfig {
}
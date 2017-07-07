package com.octopusthu.ejw.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty("ejw.autoConfiguration.enabled")
@Configuration
@EnableAutoConfiguration
public class AutoConfigurationConfig {
}
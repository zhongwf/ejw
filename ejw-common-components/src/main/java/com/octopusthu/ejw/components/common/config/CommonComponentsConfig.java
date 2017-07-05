package com.octopusthu.ejw.components.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:ejw-common-components.properties" }, encoding = "UTF-8")
public class CommonComponentsConfig {
}

package com.octopusthu.ejw.web.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan({ "${ejw.web.api.basePackages}" })
public class ApiWebApplicationContextConfig {
}

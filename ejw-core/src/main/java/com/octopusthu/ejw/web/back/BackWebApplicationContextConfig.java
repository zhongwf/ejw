package com.octopusthu.ejw.web.back;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan({ "${ejw.web.back.basePackages}" })
public class BackWebApplicationContextConfig {
}

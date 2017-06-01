package com.octopusthu.ejw.security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

}

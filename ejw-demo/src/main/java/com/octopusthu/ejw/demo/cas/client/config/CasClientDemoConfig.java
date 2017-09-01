package com.octopusthu.ejw.demo.cas.client.config;

import com.octopusthu.ejw.demo.cas.client.CasClientDemoProps;
import com.octopusthu.ejw.demo.cas.client.CasWebSecurityConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cas.ServiceProperties;

@ConditionalOnProperty("ejw.demo.cas.client.enabled")
@Configuration
public class CasClientDemoConfig {
    @Bean
    public CasWebSecurityConfig casWebSecurityConfig() {
        return new CasWebSecurityConfig();
    }

    @Bean
    public CasClientDemoProps casClientDemoProps() {
        return new CasClientDemoProps();
    }

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties props = new ServiceProperties();
        props.setService(casClientDemoProps().getService());
        return props;
    }
}
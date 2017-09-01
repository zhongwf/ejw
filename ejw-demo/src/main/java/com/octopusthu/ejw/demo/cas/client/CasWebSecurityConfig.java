package com.octopusthu.ejw.demo.cas.client;

import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.inject.Inject;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class CasWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Inject
    ServiceProperties serviceProperties;
    @Inject
    CasDemoProps casDemoProps;
    @Inject
    CasClientDemoProps casClientDemoProps;

    public CasWebSecurityConfig() {
        super(true);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher(casDemoProps.getAntPattern());
        http.securityContext();
        http.exceptionHandling().authenticationEntryPoint(casEntryPoint());
        http.addFilter(casFilter());
    }

    private CasAuthenticationEntryPoint casEntryPoint() {
        CasAuthenticationEntryPoint casEntryPoint = new CasAuthenticationEntryPoint();
        casEntryPoint.setLoginUrl(casClientDemoProps.getServer().getLoginUrl());
        casEntryPoint.setServiceProperties(serviceProperties);
        return null;
    }

    private CasAuthenticationFilter casFilter() {
        CasAuthenticationFilter casFilter = new CasAuthenticationFilter();
        casFilter.setAuthenticationManager(casAuthenticationManager());
        return casFilter;
    }

    private AuthenticationManager casAuthenticationManager() {
        return new ProviderManager(Arrays.asList(new AuthenticationProvider[]{casAuthenticationProvider()}));
    }

    private CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setAuthenticationUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService()));
        provider.setServiceProperties(serviceProperties);
        provider.setTicketValidator(ticketValidator());
        provider.setKey(casClientDemoProps.getKey());
        return provider;
    }

    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("2008980046").password("password").roles("USER", "ADMIN").build());
        manager.createUser(User.withUsername("2001990035").password("password").roles("USER").build());
        return manager;
    }

    private Cas30ServiceTicketValidator ticketValidator() {
        Cas30ServiceTicketValidator ticketValidator = new Cas30ServiceTicketValidator(casClientDemoProps.getServer().getUrl());
        return ticketValidator;
    }

}

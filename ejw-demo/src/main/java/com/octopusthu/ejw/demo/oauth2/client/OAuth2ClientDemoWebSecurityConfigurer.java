package com.octopusthu.ejw.demo.oauth2.client;

import com.octopusthu.ejw.demo.oauth2.client.config.OAuth2ClientDemoConfig;
import com.octopusthu.ejw.security.web.dflt.interfaces.AbstractDefaultWebSecurityConfigurer;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.inject.Inject;
import javax.servlet.Filter;

public class OAuth2ClientDemoWebSecurityConfigurer extends AbstractDefaultWebSecurityConfigurer {

    private OAuth2ClientDemoConfig.OAuth2ClientDemoProps oAuth2ClientDemoProps;
    private AuthorizationCodeResourceDetails casAuthorizationCodeResourceDetails;
    private ResourceServerProperties casResourceServerProperties;

    @Inject
    private OAuth2ClientContextFilter oAuth2ClientContextFilter;
    @Inject
    private OAuth2ClientContext oAuth2ClientContext;

    public OAuth2ClientDemoWebSecurityConfigurer(OAuth2ClientDemoConfig.OAuth2ClientDemoProps oAuth2ClientDemoProps, AuthorizationCodeResourceDetails casAuthorizationCodeResourceDetails
            , ResourceServerProperties casResourceServerProperties) {
        this.oAuth2ClientDemoProps = oAuth2ClientDemoProps;
        this.casAuthorizationCodeResourceDetails = casAuthorizationCodeResourceDetails;
        this.casResourceServerProperties = casResourceServerProperties;
    }

    @Override
    public void configureFilters(HttpSecurityFiltersDelegate http) {
        http.addFilterBefore(casFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(oAuth2ClientContextFilter, OAuth2ClientAuthenticationProcessingFilter.class);
    }

    private Filter casFilter() {
        OAuth2ClientAuthenticationProcessingFilter casFilter = new OAuth2ClientAuthenticationProcessingFilter(oAuth2ClientDemoProps.getCas().getFilterProcessesUrl());
        OAuth2RestTemplate casTemplate = new OAuth2RestTemplate(casAuthorizationCodeResourceDetails, oAuth2ClientContext);
        casFilter.setRestTemplate(casTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(casResourceServerProperties.getUserInfoUri(), casAuthorizationCodeResourceDetails.getClientId());
        tokenServices.setRestTemplate(casTemplate);
        casFilter.setTokenServices(tokenServices);
        return casFilter;
    }
}

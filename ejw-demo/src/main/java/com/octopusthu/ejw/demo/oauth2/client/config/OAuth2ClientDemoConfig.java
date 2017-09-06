package com.octopusthu.ejw.demo.oauth2.client.config;

import com.octopusthu.ejw.demo.oauth2.client.OAuth2ClientDemoWebSecurityConfigurer;
import com.octopusthu.ejw.security.web.dflt.interfaces.AbstractDefaultWebSecurityConfigurer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@ConditionalOnProperty("ejw.demo.oauth2.client.enabled")
@Configuration
@EnableOAuth2Client
public class OAuth2ClientDemoConfig {
    @Bean
    public AbstractDefaultWebSecurityConfigurer oAuth2ClientDemoWebSecurityConfigurer() {
        return new OAuth2ClientDemoWebSecurityConfigurer(oAuth2ClientDemoProps(), casAuthorizationCodeResourceDetails(), casResourceServerProperties());
    }

    @Bean
    @ConfigurationProperties("ejw.demo.oauth2.client")
    public OAuth2ClientDemoProps oAuth2ClientDemoProps() {
        return new OAuth2ClientDemoProps();
    }

    @Bean
    @ConfigurationProperties("ejw.demo.oauth2.client.cas.client")
    public AuthorizationCodeResourceDetails casAuthorizationCodeResourceDetails() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("ejw.demo.oauth2.client.cas.resource")
    public ResourceServerProperties casResourceServerProperties() {
        return new ResourceServerProperties();
    }

    @Getter
    @Setter
    public static class OAuth2ClientDemoProps {
        private Cas cas = new Cas();

        @Getter
        @Setter
        public static class Cas {
            String filterProcessesUrl;
        }

    }
}
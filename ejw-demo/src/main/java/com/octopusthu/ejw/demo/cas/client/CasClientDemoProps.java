package com.octopusthu.ejw.demo.cas.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ejw.demo.cas.client")
@Getter
@Setter
public class CasClientDemoProps {
    private String service;
    private String key;
    private Server server = new Server();

    @Getter
    @Setter
    public static class Server {
        private String url;
        private String loginUrl;
    }

}

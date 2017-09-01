package com.octopusthu.ejw.demo.cas.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ejw.demo.cas")
@Getter
@Setter
public class CasDemoProps {
    private String antPattern;
}

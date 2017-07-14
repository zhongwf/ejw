package com.octopusthu.ejw.web.dflt.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("ejw.web.default")
@Getter
@Setter
public class EjwWebDfltProps {
	private String[] staticResourcesPaths = {};
}

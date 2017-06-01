package com.octopusthu.ejw.security.authorization.dflt.interfaces;

import org.springframework.core.annotation.Order;
import com.octopusthu.ejw.security.authorization.dflt.DefaultAuthorizationRegistry;
import com.octopusthu.ejw.security.authorization.dflt.config.DefaultAuthorizationConfig;;

/**
 * Marker class to be extended by subclasses for customizations of
 * {@link DefaultAuthorizationConfig}
 * 
 * An implementing class should be exposed as a spring bean in order to be
 * discovered, and preferably with an {@link Order}.
 * 
 * @author zhangyu octopusthu@gmail.com
 *
 */
public abstract class AbstractDefaultAuthorizationConfigurer {
	public void configure(DefaultAuthorizationRegistry registry) {
	};
}

/**
 * 
 */
package com.octopusthu.ejw.web.client;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public class EjwRestTemplate extends RestTemplate {

	public EjwRestTemplate() {
		super();
	}

	public EjwRestTemplate(ClientHttpRequestFactory requestFactory) {
		super(requestFactory);
	}
}

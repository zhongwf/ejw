package com.octopusthu.ejw.web.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public abstract class AbstractRestClient {
	protected final Log log = LogFactory.getLog(this.getClass());

	protected RestTemplate restTemplate;

	public AbstractRestClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}

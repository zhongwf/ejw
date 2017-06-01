package com.octopusthu.ejw.web.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import com.octopusthu.ejw.web.client.EjwRestTemplate;

import lombok.Setter;
import lombok.AccessLevel;;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public abstract class AbstractSimpleApiRestTemplate extends EjwRestTemplate {
	protected final Log log = LogFactory.getLog(this.getClass());

	@Setter(AccessLevel.PROTECTED)
	private String pnameApp = "app";
	@Setter(AccessLevel.PROTECTED)
	private String pnameKey = "key";
	@Setter(AccessLevel.PROTECTED)
	private String pnameTimestamp = "timestamp";

	protected abstract String getTimestamp();

	protected abstract String calculateKey(String appId, String appKey, String timestamp);

	protected <T> T getForEntity(String url, Class<T> responseType, Map<String, String> urlVariables,
			boolean simpleApiSecurity, String appId, String appKey) {
		return getForEntity(url, responseType, urlVariables, simpleApiSecurity, appId, appKey, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getForEntity(String url, Class<T> responseType, Map<String, String> urlVariables,
			boolean simpleApiSecurity, String appId, String appKey, HttpStatus expectedHttpStatus) {
		if (simpleApiSecurity) {
			performSimpleApiSecurity(urlVariables, appId, appKey);
		}
		ResponseEntity<T> res = this.getForEntity(url, responseType,
				urlVariables != null ? urlVariables : Collections.EMPTY_MAP);
		assertResponse(res, expectedHttpStatus);
		return res.getBody();
	}

	@SuppressWarnings("unchecked")
	protected <T> T post(String url, MediaType requestContentType, Class<T> responseType,
			Map<String, String> urlVariables, String requestBody, boolean simpleApiSecurity, String appId,
			String appKey, HttpStatus expectedHttpStatus) {
		if (simpleApiSecurity) {
			performSimpleApiSecurity(urlVariables, appId, appKey);
		}
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(requestContentType);
		ResponseEntity<T> res = this.postForEntity(url, new HttpEntity<String>(requestBody, requestHeaders),
				responseType, urlVariables != null ? urlVariables : Collections.EMPTY_MAP);
		assertResponse(res, expectedHttpStatus);
		return res.getBody();
	}

	protected void performSimpleApiSecurity(Map<String, String> urlVariables, String appId, String appKey) {
		String timestamp = getTimestamp();
		if (urlVariables == null) {
			urlVariables = new HashMap<String, String>();
		}
		urlVariables.put(pnameApp, appId);
		urlVariables.put(pnameTimestamp, timestamp);
		urlVariables.put(pnameKey, this.calculateKey(appId, appKey, timestamp));
	}

	protected <T> void assertResponse(ResponseEntity<T> res, HttpStatus expectedHttpStatus) {
		Assert.isTrue(res.getStatusCode().equals(expectedHttpStatus),
				"Abnormal http status code: " + res.getStatusCode());
	}
}

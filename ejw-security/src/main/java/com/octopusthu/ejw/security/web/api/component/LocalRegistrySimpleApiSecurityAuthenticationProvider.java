package com.octopusthu.ejw.security.web.api.component;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.octopusthu.ejw.security.web.api.interfaces.SimpleApiSecurityAuthenticationProvider;

public class LocalRegistrySimpleApiSecurityAuthenticationProvider implements SimpleApiSecurityAuthenticationProvider {
	private Log log = LogFactory.getLog(this.getClass());

	private Map<String, String> registry = new Hashtable<String, String>();

	public LocalRegistrySimpleApiSecurityAuthenticationProvider(String[] strRegistry) throws IllegalArgumentException {
		try {
			for (String app : strRegistry) {
				String[] arr = StringUtils.commaDelimitedListToStringArray(app);
				registry.put(arr[0], arr[1]);
			}
		} catch (Exception e) {
			log.warn(e, e);
			throw new IllegalArgumentException("Malformed app registry: " + strRegistry);
		}
	}

	@Override
	public String getAppKey(String appId) {
		return registry.get(appId);
	}

	@Override
	public String calculateKey(String appId, String appKey, String ts) {
		return DigestUtils.md5Hex(appId + ts + appKey);
	}

}

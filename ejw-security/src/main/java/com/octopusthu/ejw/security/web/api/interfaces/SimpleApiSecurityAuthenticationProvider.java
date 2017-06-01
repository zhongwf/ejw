package com.octopusthu.ejw.security.web.api.interfaces;

public interface SimpleApiSecurityAuthenticationProvider {
	public String getAppKey(String appId);

	public String calculateKey(String appId, String appKey, String ts);
}

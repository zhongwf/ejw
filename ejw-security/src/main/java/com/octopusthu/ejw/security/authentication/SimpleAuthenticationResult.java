package com.octopusthu.ejw.security.authentication;

import org.springframework.util.Assert;

public class SimpleAuthenticationResult implements AuthenticationResult {
	private boolean successful;
	private String username;
	private Object details;

	public SimpleAuthenticationResult(String username, Object details) {
		Assert.hasText(username, "username must not be empty!");
		this.successful = true;
		this.username = username;
		this.details = details;
	}

	@Override
	public boolean isSuccessful() {
		return this.successful;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public Object getDetails() {
		return this.details;
	}

}

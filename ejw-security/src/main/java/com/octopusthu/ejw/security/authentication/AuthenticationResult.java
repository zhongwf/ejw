package com.octopusthu.ejw.security.authentication;

public interface AuthenticationResult {
	public boolean isSuccessful();

	public String getUsername();

	public Object getDetails();
}

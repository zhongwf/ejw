/**
 * 
 */
package com.octopusthu.ejw.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public class AuthenticationExceptionImpl extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	public AuthenticationExceptionImpl(String msg) {
		super(msg);
	}

	public AuthenticationExceptionImpl(String msg, Throwable t) {
		super(msg, t);
	}
}

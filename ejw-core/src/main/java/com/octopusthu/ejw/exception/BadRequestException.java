package com.octopusthu.ejw.exception;

public class BadRequestException extends GenericException {
	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String msg) {
		super(msg);
	}
}

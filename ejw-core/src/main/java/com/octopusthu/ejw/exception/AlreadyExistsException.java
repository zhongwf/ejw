package com.octopusthu.ejw.exception;

public class AlreadyExistsException extends GenericException {
	private static final long serialVersionUID = 1L;

	public AlreadyExistsException(String msg) {
		super(msg);
	}
}

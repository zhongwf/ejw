/**
 * 
 */
package com.octopusthu.ejw.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public class GenericException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Object obj; // object to be serialized and passed to the front end

	public GenericException() {
		super();
	}

	public GenericException(String msg) {
		this(msg, null);
	}

	public GenericException(String msg, Object obj) {
		super(msg);
		this.obj = obj;
	}
}

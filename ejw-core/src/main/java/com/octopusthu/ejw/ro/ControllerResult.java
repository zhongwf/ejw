package com.octopusthu.ejw.ro;

import lombok.Getter;
import lombok.Setter;

public class ControllerResult {
	public final static char SUCCESS = '1';
	public final static char ERROR = '2';

	protected final static String SUCCESS_STR = "success";
	protected final static String ERROR_STR = "error";

	private final static ControllerResult SUCCESS_OBJ = new ControllerResult(SUCCESS_STR);
	private final static ControllerResult ERROR_OBJ = new ControllerResult(ERROR_STR);

	@Getter
	@Setter
	private String result;
	@Getter
	@Setter
	private String msg;
	@Getter
	@Setter
	private Object object;

	protected ControllerResult(String result) {
		this.result = result;
	}

	public static ControllerResult valueOf(char result) {
		switch (result) {
		case SUCCESS:
			return SUCCESS_OBJ;
		case ERROR:
			return ERROR_OBJ;
		default:
			return null;
		}
	}

	public static ControllerResult valueOf(char result, String msg) {
		ControllerResult r;
		switch (result) {
		case SUCCESS:
			r = new ControllerResult(SUCCESS_STR);
			break;
		case ERROR:
			r = new ControllerResult(ERROR_STR);
			break;
		default:
			return null;
		}
		r.setMsg(msg);
		return r;
	}

	public static ControllerResult valueOf(char result, String msg, Object object) {
		ControllerResult r = ControllerResult.valueOf(result, msg);
		if (r == null) {
			return null;
		}
		r.setObject(object);
		return r;
	}

}
/**
 * 
 */
package com.octopusthu.ejw.web.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.octopusthu.ejw.exception.BadRequestException;
import com.octopusthu.ejw.exception.GenericException;
import com.octopusthu.ejw.exception.InternalServerException;
import com.octopusthu.ejw.exception.NotFoundException;
import com.octopusthu.ejw.exception.SecurityContextException;
import com.octopusthu.ejw.ro.ControllerResult;

/**
 * @author zhangyu octopusthu@gmail.com
 */
@RestControllerAdvice
public class ApiAdviceController {

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(Exception.class)
	public ControllerResult handleException(Exception ex) {
		return ControllerResult.valueOf(ControllerResult.ERROR, "Unknown Error!");
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(InternalServerException.class)
	public ControllerResult handleInternalServerException(InternalServerException ex) {
		return ControllerResult.valueOf(ControllerResult.ERROR, ex.getMessage());
	}

	@ResponseStatus(value = HttpStatus.OK) // 200
	@ExceptionHandler(GenericException.class)
	public ControllerResult handleGenericException(GenericException ex) {
		return ControllerResult.valueOf(ControllerResult.ERROR, ex.getMessage());
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler({ BadRequestException.class })
	public ControllerResult handleBadRequestException(BadRequestException ex) {
		return ControllerResult.valueOf(ControllerResult.ERROR, ex.getMessage());
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler({ MissingPathVariableException.class, MissingServletRequestParameterException.class,
			MethodArgumentNotValidException.class })
	public ControllerResult handleSpringControllerBadRequestException(Exception ex) {
		return ControllerResult.valueOf(ControllerResult.ERROR, ex.getMessage());
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN) // 403
	@ExceptionHandler(SecurityContextException.class)
	public ControllerResult handleSecurityContextException(SecurityContextException ex) {
		return ControllerResult.valueOf(ControllerResult.ERROR, ex.getMessage());
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND) // 404
	@ExceptionHandler(NotFoundException.class)
	public @ResponseBody ControllerResult handleNotFoundException(NotFoundException ex) {
		return ControllerResult.valueOf(ControllerResult.ERROR, ex.getMessage());
	}

	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED) // 405
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public @ResponseBody ControllerResult handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException ex) {
		return ControllerResult.valueOf(ControllerResult.ERROR, ex.getMessage());
	}

}
/**
 * 
 */
package com.octopusthu.ejw.web.front.controller;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.octopusthu.ejw.exception.BadRequestException;
import com.octopusthu.ejw.exception.GenericException;
import com.octopusthu.ejw.exception.InternalServerException;
import com.octopusthu.ejw.exception.NotFoundException;
import com.octopusthu.ejw.exception.RequestContextException;
import com.octopusthu.ejw.exception.SecurityContextException;

/**
 * @author zhangyu octopusthu@gmail.com
 */
@ControllerAdvice
public class FrontAdviceController {
	protected final Log log = LogFactory.getLog(this.getClass());

	@Inject
	MessageSource messages;

	@Value("${ejw.web.front.advice.errorView:error}")
	private String errorView;

	// 200
	@ExceptionHandler
	public String handleException(Exception e) {
		log.info("Exception occurred！", e);
		return errorView;
	}

	// 200
	@ExceptionHandler({ GenericException.class })
	public ModelAndView handleGenericException(GenericException ex) {
		log.info("GenericException occurred！", ex);
		ModelAndView mav = new ModelAndView(errorView);
		String msg = ex.getMessage();
		mav.addObject("msg", msg != null ? msg
				: messages.getMessage("ejw_error_unknownError", null, LocaleContextHolder.getLocale()));
		return mav;
	}

	// 200
	@ExceptionHandler({ RequestContextException.class })
	public ModelAndView handleRequestAuthenticationException(RequestContextException ex) {
		log.info("RequestAuthenticationException occurred！", ex);
		ModelAndView mav = new ModelAndView(errorView);
		mav.addObject("msg", "用户请求异常");
		return mav;
	}

	// 400
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad Request!")
	@ExceptionHandler(BadRequestException.class)
	public void handleBadRequestException() {
	}

	// 403
	@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Access Denied!")
	@ExceptionHandler(SecurityContextException.class)
	public void handleSecurityContextException() {
	}

	// 404
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource Not Found!")
	@ExceptionHandler(NotFoundException.class)
	public void handleResourceNotFoundException() {
	}

	// 500
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Server Exception!")
	@ExceptionHandler(InternalServerException.class)
	public void handleException() {
	}

}
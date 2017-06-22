/**
 * 
 */
package com.octopusthu.ejw.web.back.controller;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.octopusthu.ejw.exception.BadRequestException;
import com.octopusthu.ejw.exception.DataInconsistentException;
import com.octopusthu.ejw.exception.DataVerificationException;
import com.octopusthu.ejw.exception.GenericException;
import com.octopusthu.ejw.exception.RequestContextException;
import com.octopusthu.ejw.exception.SecurityContextException;
import com.octopusthu.ejw.ro.ControllerResult;

/**
 * @author zhangyu octopusthu@gmail.com
 */
@RestControllerAdvice
public class BackAdviceController {
	protected static final Log log = LogFactory.getLog(BackAdviceController.class);

	@Inject
	MessageSource messages;

	@ExceptionHandler({ IllegalArgumentException.class })
	public ControllerResult handleIllegalArgumentException(IllegalArgumentException ex) {
		log.info("IllegalArgumentException occurred: " + ex.getMessage(), ex);
		return ControllerResult.valueOf(ControllerResult.ERROR, ex.getMessage());
	}

	@ExceptionHandler({ BindException.class })
	public ControllerResult handleBindException(BindException ex) {
		log.info("BindException occurred: " + ex.getMessage(), ex);
		return ControllerResult.valueOf(ControllerResult.ERROR,
				ex.getBindingResult().getFieldError().getDefaultMessage());
	}

	@ExceptionHandler({ DataVerificationException.class })
	public ControllerResult handleDataVerificationException(DataVerificationException ex) {
		log.info("DataVerificationException occurred: " + ex.getMessage(), ex);
		String msg = ex.getMessage();
		return ControllerResult.valueOf(ControllerResult.ERROR, msg != null ? msg
				: messages.getMessage("ejw_error_dataVerificationException", null, LocaleContextHolder.getLocale()));
	}

	@ExceptionHandler({ BadRequestException.class })
	public ControllerResult handleBadRequestException(BadRequestException ex) {
		log.info("BadRequestException occurred: " + ex.getMessage(), ex);
		String msg = ex.getMessage();
		return ControllerResult.valueOf(ControllerResult.ERROR, msg != null ? msg
				: messages.getMessage("ejw_error_badRequestException", null, LocaleContextHolder.getLocale()));
	}

	@ExceptionHandler({ RequestContextException.class })
	public ControllerResult handleRequestContextException(RequestContextException ex) {
		log.info("RequestContextException occurred: " + ex.getMessage(), ex);
		return ControllerResult.valueOf(ControllerResult.ERROR,
				messages.getMessage("ejw_error_requestContextException", null, LocaleContextHolder.getLocale()));
	}

	@ExceptionHandler({ SecurityContextException.class })
	public ControllerResult handleSecurityContextException(SecurityContextException ex) {
		log.info("SecurityContextException occurred: " + ex.getMessage(), ex);
		return ControllerResult.valueOf(ControllerResult.ERROR,
				messages.getMessage("ejw_error_securityContextException", null, LocaleContextHolder.getLocale()));
	}

	@ExceptionHandler({ DataInconsistentException.class })
	public ControllerResult handleDataInconsistentException(DataInconsistentException ex) {
		log.info("DataInconsistentException occurred: " + ex.getMessage(), ex);
		return ControllerResult.valueOf(ControllerResult.ERROR,
				messages.getMessage("ejw_error_dataInconsistentException", null, LocaleContextHolder.getLocale()));
	}

	@ExceptionHandler({ GenericException.class })
	public ControllerResult handleGenericException(GenericException ex) {
		log.info("GenericException occurred: " + ex.getMessage(), ex);
		String msg = ex.getMessage();
		return ControllerResult.valueOf(ControllerResult.ERROR,
				msg != null ? msg
						: messages.getMessage("ejw_error_unknownError", null, LocaleContextHolder.getLocale()),
				ex.getObj());
	}

	@ExceptionHandler
	public ControllerResult handleException(Exception e) {
		log.error("Exception occurred！", e);
		return ControllerResult.valueOf(ControllerResult.ERROR,
				messages.getMessage("ejw_error_unknownError", null, LocaleContextHolder.getLocale()));
	}

}
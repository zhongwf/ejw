package com.octopusthu.ejw.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.octopusthu.ejw.exception.RequestContextException;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public class RequestContextUtils {
	protected static final Log log = LogFactory.getLog(RequestContextUtils.class);

	public static HttpServletRequest getHttpServletRequest() throws RequestContextException {
		HttpServletRequest req;
		try {
			req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		} catch (Exception e) {
			log.error(e, e);
			throw new RequestContextException(e.getMessage());
		}
		if (req == null) {
			throw new RequestContextException("Exception retrieving HttpServletRequest");
		}
		return req;
	}

	public static HttpSession getSession(boolean create) throws RequestContextException {
		return getHttpServletRequest().getSession(create);
	}

	public static HttpSession getSession() throws RequestContextException {
		return getSession(false);
	}

	public static Object getSessionAttribute(String name) throws RequestContextException {
		return getSession(false).getAttribute(name);
	}

	public static void setSessionAttribute(String name, Object value) throws RequestContextException {
		getSession(false).setAttribute(name, value);
	}

	public static ServletContext getServletContext() throws RequestContextException {
		try {
			return getSession().getServletContext();
		} catch (NullPointerException e) {
			log.error("session is null", e);
			throw new RequestContextException(e.getMessage());
		}
	}

	public static String getUserIp() throws RequestContextException {
		return getHttpServletRequest().getRemoteAddr();
	}

	public static String getRequestHeader(String header) throws RequestContextException {
		return getHttpServletRequest().getHeader(header);
	}

	public static String getUserAgentString() throws RequestContextException {
		return getRequestHeader("User-Agent");
	}
}

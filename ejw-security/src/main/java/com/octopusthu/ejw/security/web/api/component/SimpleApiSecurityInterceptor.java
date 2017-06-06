package com.octopusthu.ejw.security.web.api.component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.octopusthu.ejw.security.component.SecurityProps;
import com.octopusthu.ejw.security.web.api.interfaces.SimpleApiSecurityAuthenticationProvider;

/**
 * 通过 App ID 和 App Key 对 RESTful Service 客户端进行权限验证。
 * 
 * 1) 通过 System.currentTimeMillis()/1000 或从 /a/public/timestamp 获得服务器时间戳ts
 * 
 * 2) key = md5(appid + t + appkey)
 * 
 * 3) 调用接口时携带 timestamp, app, key 三个参数，例如：
 * 
 * https://{baseURL}/api/get?timestamp={ts}&app={appid}&key={key}
 * 
 * @author zhangyu octopusthu@gmail.com
 *
 */
public class SimpleApiSecurityInterceptor extends AbstractApiSecurityInterceptor {
	private final Log log = LogFactory.getLog(this.getClass());

	private SecurityProps.Api.Simple props;
	private SimpleApiSecurityAuthenticationProvider provider;

	private static final String DENY_REASON_BAD_REQUEST = "Bad request.";
	private static final String DENY_REASON_TIME_DEVIATION = "Time out of sync.";
	private static final String DENY_REASON_BAD_CREDETIALS = "Bad credentials.";
	private static final String DENY_REASON_INSUFFICIENT_PRIVILEGES = "Insufficient privileges.";

	public SimpleApiSecurityInterceptor(SecurityProps props, SimpleApiSecurityAuthenticationProvider provider) {
		this.props = props.getApi().getSimple();
		this.provider = provider;
	}

	@Override
	protected boolean shouldProcess(HttpServletRequest req) {
		Set<String> set = req.getParameterMap().keySet();
		return set == null ? false
				: (set.contains(props.getParams().getApp()) && set.contains(props.getParams().getKey())
						&& set.contains(props.getParams().getTimestamp()));
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String appId = req.getParameter(props.getParams().getApp());
		String key = req.getParameter(props.getParams().getKey());
		String ts = req.getParameter(props.getParams().getTimestamp());
		if (!this.verifyParamFormats(ts, appId, key)) {
			accessDenied(req, res, chain, this.getClass(), DENY_REASON_BAD_REQUEST);
			return;
		}

		if (Math.abs(Long.parseLong(ts) - System.currentTimeMillis() / 1000) > props.getTimeDeviationAllowed()) {
			accessDenied(req, res, chain, this.getClass(), DENY_REASON_TIME_DEVIATION);
			return;
		}

		if (!checkPrivilege(appId)) {
			accessDenied(req, res, chain, this.getClass(), DENY_REASON_INSUFFICIENT_PRIVILEGES);
		}

		String appKey = provider.getAppKey(appId);
		String calculatedKey = provider.calculateKey(appId, appKey, ts);
		if (!key.equals(calculatedKey)) {
			accessDenied(req, res, chain, this.getClass(), DENY_REASON_BAD_CREDETIALS);
		}

		this.accessGranted(req, res, chain);
	}

	protected boolean verifyParamFormats(String ts, String appid, String key) {
		try {
			Long.parseLong(ts);
			Assert.hasText(appid, "AppId not found!");
			if (key.length() != 32) {
				throw new Exception();
			}
		} catch (Exception e) {
			log.warn("Bad Request: ts=" + ts + ", appid=" + appid + ", key=" + key, e);
			return false;
		}
		return true;
	}

	protected boolean checkPrivilege(String appId) {
		return props.isAppIdWhiteListEnabled() ? Arrays.asList(props.getAppIdWhiteList()).contains(appId) : true;
	}
}

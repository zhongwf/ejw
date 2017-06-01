package com.octopusthu.ejw.security.component;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.security.web.util.UrlUtils;
import lombok.Setter;

/**
 * An alternative to {@link DefaultRedirectStrategy}, adding support of forcing
 * HTTPS redirection, yet removing the concept of {@code contextRelative},
 * assuming that urls passed in never contain the servlet context path.
 * 
 * @author zhangyu octopusthu@gmail.com
 */
public class HttpsAwareRedirectStrategy implements RedirectStrategy {
	private final Log log = LogFactory.getLog(getClass());

	@Setter
	private Boolean forceHttps;
	@Setter
	private PortResolver portResolver;
	@Setter
	private PortMapper portMapper;

	@Override
	public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		String redirectUrl = calculateRedirectUrl(request, request.getContextPath(), url);
		redirectUrl = response.encodeRedirectURL(redirectUrl);

		if (log.isDebugEnabled()) {
			log.debug("Redirecting to '" + redirectUrl + "'");
		}

		response.sendRedirect(redirectUrl);
	}

	/**
	 * A simulation to
	 * LoginUrlAuthenticationEntryPoint.buildRedirectUrlToLoginPage
	 */
	protected String calculateRedirectUrl(HttpServletRequest request, String contextPath, String url) {
		if (UrlUtils.isAbsoluteUrl(url)) {
			return url;
		}

		if (!url.startsWith("/")) {
			url = "/" + url;
		}

		int serverPort = portResolver.getServerPort(request);
		String scheme = request.getScheme();

		RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
		urlBuilder.setScheme(scheme);
		urlBuilder.setPort(serverPort);
		urlBuilder.setServerName(request.getServerName());
		urlBuilder.setContextPath(request.getContextPath());
		urlBuilder.setPathInfo(url);

		if (forceHttps && "http".equals(scheme)) {
			Integer httpsPort = portMapper.lookupHttpsPort(Integer.valueOf(serverPort));
			if (httpsPort != null) {
				urlBuilder.setScheme("https");
				urlBuilder.setPort(httpsPort.intValue());
			} else {
				log.warn("Unable to redirect to HTTPS as no port mapping found for HTTP port " + serverPort);
			}
		}

		return urlBuilder.getUrl();
	}

}

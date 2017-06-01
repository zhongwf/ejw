/**
 * 
 */
package com.octopusthu.ejw.components.i18n.web.front.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.octopusthu.ejw.web.front.controller.FrontBaseController;

/**
 * @author zhangyu octopusthu@gmail.com
 */
@ConditionalOnProperty("ejw.components.i18n.changeLocaleFrontController.enabled")
@Controller
@RequestMapping("${ejw.components.i18n.changeLocaleFrontController.mapping:/common}")
public class ChangeLocaleFrontController extends FrontBaseController {
	private Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "${ejw.components.i18n.changeLocaleFrontController.changeLocale.mapping:/public/locale/change}", method = RequestMethod.POST)
	public RedirectView changeLocale(HttpServletRequest request,
			@Value("${ejw.components.i18n.changeLocaleFrontController.changeLocale.targetUrlParameter:target}") String targetUrlParameter) {
		String redirect = request.getParameter(targetUrlParameter);
		if (redirect == null) {
			log.warn("Cannot find targetUrlParameter, redirecting to '/'.");
		}
		RedirectView redirectView = new RedirectView(redirect);
		redirectView.setExposeModelAttributes(false);
		return redirectView;
	}
}
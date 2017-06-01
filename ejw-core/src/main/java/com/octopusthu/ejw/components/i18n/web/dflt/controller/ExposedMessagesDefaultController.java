/**
 * 
 */
package com.octopusthu.ejw.components.i18n.web.dflt.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octopusthu.ejw.components.i18n.ExposedResourceBundleMessageSourceService;
import com.octopusthu.ejw.web.dflt.controller.DefaultBaseController;

/**
 * @author zhangyu octopusthu@gmail.com
 */
@ConditionalOnProperty("ejw.components.i18n.exposedMessagesDefaultController.enabled")
@Controller
@RequestMapping("${ejw.components.i18n.exposedMessagesDefaultController.mapping:/common}")
public class ExposedMessagesDefaultController extends DefaultBaseController {

	@Inject
	ExposedResourceBundleMessageSourceService messageSourceService;

	@GetMapping("${ejw.components.i18n.exposedMessagesDefaultController.allMessages.mapping:/public/all-messages.js}")
	public void allMessagesJs(HttpServletResponse response,
			@Value("${ejw.components.i18n.exposedMessagesDefaultController.allMessages.contentType:application/javascript}") String contentType,
			@Value("${ejw.components.i18n.exposedMessagesDefaultController.allMessages.characterEncoding:UTF-8}") String characterEncoding,
			@Value("${ejw.components.i18n.exposedMessagesDefaultController.allMessages.varName:messages}") String varName)
			throws IOException {
		response.setContentType(contentType);
		response.setCharacterEncoding(characterEncoding);
		response.getWriter().println("var " + varName + " = " + messageSourceService.getAllMessagesJsonString() + ";");
	}
}
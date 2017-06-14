package com.octopusthu.ejw.security.test.web.back.controller;

import javax.inject.Inject;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.octopusthu.ejw.ro.ControllerResult;
import com.octopusthu.ejw.security.util.SecurityContextUtils;
import com.octopusthu.ejw.web.back.controller.BackBaseController;

@Profile({ "test", "dev", "simu" })
@RestController
@RequestMapping("/test/security-context")
public class SecurityContextTestBackController extends BackBaseController {
	@Inject
	WebInvocationPrivilegeEvaluator webInvocationPrivilegeEvaluator;
	@Inject
	SecurityExpressionHandler<FilterInvocation> securityExpressionHandler;

	@RequestMapping("/authentication")
	public ControllerResult authentication() {
		return ControllerResult.valueOf(ControllerResult.SUCCESS, "", SecurityContextUtils.getAuthentication());
	}

	@RequestMapping("/user-details")
	public ControllerResult getUserDetails() {
		return ControllerResult.valueOf(ControllerResult.SUCCESS, "", SecurityContextUtils.getUserDetails());
	}

	@RequestMapping("/user-id")
	public ControllerResult getUserId() {
		return ControllerResult.valueOf(ControllerResult.SUCCESS, "", SecurityContextUtils.getUserId());
	}

	@RequestMapping("/user-authorities")
	public ControllerResult getUserAuthorites() {
		return ControllerResult.valueOf(ControllerResult.SUCCESS, "", SecurityContextUtils.getUserAuthorites());
	}

	@RequestMapping("/local-roles")
	public ControllerResult getLocalRoles() {
		return ControllerResult.valueOf(ControllerResult.SUCCESS, "", SecurityContextUtils.getLocalRoles());
	}

}

package com.octopusthu.ejw.test.web.back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.octopusthu.ejw.ro.ControllerResult;
import com.octopusthu.ejw.security.util.SecurityContextUtils;
import com.octopusthu.ejw.web.back.controller.BackBaseController;

@RestController
@RequestMapping("/test/security-context")
public class SecurityContextTestBackController extends BackBaseController {

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

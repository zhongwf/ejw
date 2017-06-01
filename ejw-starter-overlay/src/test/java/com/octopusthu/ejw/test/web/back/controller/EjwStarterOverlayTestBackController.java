package com.octopusthu.ejw.test.web.back.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.octopusthu.ejw.ro.ControllerResult;
import com.octopusthu.ejw.test.service.EjwStarterOverlayTestService;
import com.octopusthu.ejw.web.back.controller.BackBaseController;

@RestController
@RequestMapping("/test")
public class EjwStarterOverlayTestBackController extends BackBaseController {

	@Inject
	private EjwStarterOverlayTestService service;

	@RequestMapping("/test")
	public ControllerResult test() {
		return ControllerResult.valueOf(ControllerResult.SUCCESS, "", service.getTest());
	}

	@GetMapping("/all-messages")
	public ControllerResult allMessages() {
		return ControllerResult.valueOf(ControllerResult.SUCCESS, "", service.getAllMessages());
	}

}

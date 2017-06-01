/**
 * 
 */
package com.octopusthu.ejw.web.back.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.octopusthu.ejw.ro.ControllerResult;

/**
 * @author zhangyu octopusthu@gmail.com
 */
@ConditionalOnProperty("ejw.web.back.ejwBackCommonController.mapping")
@RestController
@RequestMapping("${ejw.web.back.ejwBackCommonController.mapping}")
public class EjwBackCommonController extends BackBaseController {

	@GetMapping("${ejw.web.back.ejwBackCommonController.hello.mapping:/public/hello}")
	public ControllerResult hello() {
		return ControllerResult.valueOf(ControllerResult.SUCCESS, "Hello!");
	}
}
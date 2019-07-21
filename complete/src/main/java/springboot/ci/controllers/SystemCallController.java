package springboot.ci.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.ci.services.SystemCalls;

@RestController
public class SystemCallController {

	@RequestMapping(value = "/systest", produces = "application/json")
	public Map<String,Object> index() {
		System.out.println("SYSTEST");
		Map<String,Object> ret = SystemCalls.test();
		return ret;
	}
}

package springboot.ci.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.ci.services.SystemCalls;

@RestController
public class CI_SystemCalls {

	@RequestMapping("/systest")
	public String index() {
		System.out.println("SYSTEST");
		String ret = SystemCalls.test();
		return ret;
	}
}

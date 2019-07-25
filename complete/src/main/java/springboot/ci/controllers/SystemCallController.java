package springboot.ci.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.ci.services.SystemCalls;

@RestController
public class SystemCallController {

	static final String CMD = "SYSTEM CMD";

	@GetMapping(value = "/system", produces = "application/json")
	public Map<String, Object> systemGet(@RequestParam(value = "cmd", required = false, defaultValue = "") String cmd,
			@RequestParam(value = "respMode", required = false, defaultValue = "HTML") String mode) {
		LinkedHashMap<String, Object> requestLHM = new LinkedHashMap<String, Object>();
		requestLHM.put(CMD, cmd);
		requestLHM.put("MODE", mode);

		System.out.println("EXECUTING CMD = " + requestLHM.toString());
		LinkedHashMap<String, Object> responceLHM = SystemCalls.execSysCmd(requestLHM);

		System.out.println("CMD RESPONSE = \n" + requestLHM.toString());
		return responceLHM;
	}

	@PostMapping(value = "/system", produces = "application/json")
	public Map<String, Object> systemPost(@RequestBody LinkedHashMap<String, Object> requestLHM) {
		System.out.println("EXECUTING CMD = " + requestLHM.toString());
		LinkedHashMap<String, Object> responceLHM = SystemCalls.execSysCmd(requestLHM);

		System.out.println("CMD RESPONSE = \n" + requestLHM.toString());
		return responceLHM;
	}
}

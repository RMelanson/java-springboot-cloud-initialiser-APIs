package springboot.ci.controllers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.ci.services.SystemCalls;

@CrossOrigin("*")
@RestController
public class SystemCallController {

	static final String CMD = "CMD";

	// GET MAPPINGS
	
	@GetMapping(value = "/parmsTest", produces = "application/json")
	public Map<String, Object> parmsGetTest(@RequestParam LinkedHashMap<String, Object> requestLHM) {

		System.out.println("EXECUTING PARMS TEST = " + requestLHM.toString());
		SystemCalls.setUpperCaseKeys(requestLHM);

		LinkedHashMap<String, Object> responseLHM = new LinkedHashMap<String, Object>();
		responseLHM.put("REQUEST", requestLHM);

		System.out.println("GET CMD responseLHM = \n" + requestLHM.toString());
		return responseLHM;
	}

	@GetMapping(value = "/system", produces = "application/json")
	public Map<String, Object> systemGet(@RequestParam LinkedHashMap<String, Object> requestLHM) {

		System.out.println("EXECUTING GET CMD = " + requestLHM.toString());

		SystemCalls.setUpperCaseKeys(requestLHM);

		Set<String> keys = requestLHM.keySet();
		keys.parallelStream();

		LinkedHashMap<String, Object> responseLHM = SystemCalls.execGet(requestLHM);

		System.out.println("GET CMD responseLHM = \n" + requestLHM.toString());
		return responseLHM;
	}

	// POST MAPPINGS

	@PostMapping(value = "/parmsTest", produces = "application/json")
	public Map<String, Object> parmsPostTest(@RequestParam LinkedHashMap<String, Object> requestPARMS,
			@RequestBody LinkedHashMap<String, Object> requestLHM) {
		
		System.out.println("EXECUTING PARMS TEST = " + requestLHM.toString());
		SystemCalls.setUpperCaseKeys(requestLHM);

		LinkedHashMap<String, Object> responseLHM = new LinkedHashMap<String, Object>();
		responseLHM.put("REQUEST", requestLHM);

		System.out.println("GET CMD responseLHM = \n" + requestLHM.toString());
		return responseLHM;
	}

	@PostMapping(value = "/system", produces = "application/json")
	public Map<String, Object> systemPost(@RequestParam LinkedHashMap<String, Object> requestPARMS,
			@RequestBody LinkedHashMap<String, Object> requestLHM) {

		SystemCalls.setUpperCaseKeys(requestLHM);

		System.out.println("EXECUTING POST CMD = " + requestLHM.toString());
		LinkedHashMap<String, Object> responseLHM = SystemCalls.execPost(requestLHM);

		System.out.println("POST CMD responseLHM = \n" + requestLHM.toString());
		return responseLHM;
	}
}

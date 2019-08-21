package springboot.ci.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

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
	public Map<String, Object> parmsGetTest(@RequestParam LinkedHashMap<String, Object> requestParms) {
		LinkedHashMap<String, Object> responseLHM = SystemCalls.getResponseLHM("/parmsTest", "Get", requestParms);
		return responseLHM;
	}

	@GetMapping(value = "/system", produces = "application/json")
	public Map<String, Object> systemGet(@RequestParam LinkedHashMap<String, Object> requestParms) {
		LinkedHashMap<String, Object> responseLHM = SystemCalls.getResponseLHM("/system", "Get", requestParms);
		return responseLHM;
	}

	// POST MAPPINGS

	@PostMapping(value = "/parmsTest", produces = "application/json")
	public Map<String, Object> parmsPostTest(@RequestParam LinkedHashMap<String, Object> requestParms,
			@RequestBody LinkedHashMap<String, Object> requestBody) {
		
		LinkedHashMap<String, Object> responseLHM = SystemCalls.postResponseLHM("/parmsTest", "Get", requestParms, requestBody);
		return responseLHM;
	}

	@PostMapping(value = "/system", produces = "application/json")
	public Map<String, Object> systemPost(@RequestParam LinkedHashMap<String, Object> requestParms,
			@RequestBody LinkedHashMap<String, Object> requestBody) {

		LinkedHashMap<String, Object> responseLHM = SystemCalls.postResponseLHM("/system", "Get", requestParms, requestBody);
		return responseLHM;
	}
}

package springboot.ci.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.ci.services.InstallServices;

@CrossOrigin("*")
@RestController
public class InstallationController {

	static final String CMD = "CMD";

	// GET MAPPINGS
	
	@GetMapping(value = "/install", produces = "application/json")
	public Map<String, Object> systemGet(@RequestParam LinkedHashMap<String, Object> requestParms) {
		LinkedHashMap<String, Object> responseLHM = InstallServices.getResponseLHM("/install", "Get", requestParms);
		return responseLHM;
	}

	// POST MAPPINGS

	@PostMapping(value = "/install", produces = "application/json")
	public Map<String, Object> systemPost(@RequestParam LinkedHashMap<String, Object> requestParms,
			@RequestBody LinkedHashMap<String, Object> requestBody) {

		LinkedHashMap<String, Object> responseLHM = InstallServices.postResponseLHM("/install", "Get", requestParms, requestBody);
		return responseLHM;
	}
}

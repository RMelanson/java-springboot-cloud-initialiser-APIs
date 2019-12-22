package springboot.ci.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.ci.services.InstallServices;

@CrossOrigin("*")
@RestController
public class InstallationController {
	// GET MAPPINGS
	
	@GetMapping(value = "/install/{app}", produces = "application/json")
	public Map<String, Object> installGet(@RequestParam LinkedHashMap<String, Object> requestParms, @PathVariable("app") String app) {
		System.out.println("EXECUTING: systemGet("+requestParms+")");
		LinkedHashMap<String, Object> responseLHM = InstallServices.getResponseLHM("/install", app, "Get", requestParms);
		return responseLHM;
	}

	// POST MAPPINGS

	@PostMapping(value = "/install/{app}", produces = "application/json")
	public Map<String, Object> installPost(@RequestParam LinkedHashMap<String, Object> requestParms, @PathVariable("app") String app,
			@RequestBody LinkedHashMap<String, Object> requestBody) {
		System.out.println("EXECUTING: installPost("+requestParms+")");

		LinkedHashMap<String, Object> responseLHM = InstallServices.postResponseLHM("/install", app, "Get", requestParms, requestBody);
		return responseLHM;
	}
}

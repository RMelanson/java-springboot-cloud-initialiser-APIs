package springboot.ci.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.ci.services.Convert_Service;
import springboot.ci.services.SystemCallServices;

@CrossOrigin("*")
@RestController
public class Convert_Controller {

	static final String CMD = "CMD";

	// GET MAPPINGS
	
	@GetMapping(value = "/xmltojson", produces = "application/json")
	public Map<String, Object> getXMLtoJSON(@RequestParam LinkedHashMap<String, Object> requestParms) {
		LinkedHashMap<String, Object> responseLHM = SystemCallServices.getResponseLHM("/system", "Get", requestParms);
		return responseLHM;
	}

	// POST MAPPINGS

/*
	@PostMapping(value = "/xmltojson", consumes = "application/xml", produces = "application/json")
	public LinkedHashMap<String, Object> postXMLtoJSON(@RequestParam LinkedHashMap<String, Object> requestParms,
			@RequestBody LinkedHashMap<String, Object> requestBody) {

		return requestBody;
	}
*/
	
	@PostMapping(value = "/texttojson", consumes = "application/xml", produces = "application/json")
	public String postTEXTtoJSON(@RequestParam LinkedHashMap<String, Object> requestParms,
			@RequestBody String requestBody) {

		return requestBody;
	}
	
	@PostMapping(value = "/convert/xmltojson", consumes = "application/xml", produces = "application/json")
	public String postXMLtoJSON(@RequestParam LinkedHashMap<String, Object> requestParms,
			@RequestBody String requestBody) {

		return Convert_Service.xmlToJson(requestBody);
	}
	
}

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
	public Map<String, Object> parmsGetTest(@RequestParam LinkedHashMap<String, Object> requestParms) {

		System.out.println("EXECUTING PARMS TEST = " + requestParms.toString());
		SystemCalls.setUpperCaseKeys(requestParms);

		LinkedHashMap<String, Object> responseLHM = new LinkedHashMap<String, Object>();
		responseLHM.put("REQUEST PARMS", requestParms);

		System.out.println("GET CMD responseLHM = \n" + requestParms.toString());
		return responseLHM;
	}

	@GetMapping(value = "/system", produces = "application/json")
	public Map<String, Object> systemGet(@RequestParam LinkedHashMap<String, Object> requestParms) {

		System.out.println("EXECUTING GET CMD = " + requestParms.toString());

		SystemCalls.setUpperCaseKeys(requestParms);

		Set<String> keys = requestParms.keySet();
		keys.parallelStream();

		LinkedHashMap<String, Object> responseLHM = SystemCalls.execGet(requestParms);

		System.out.println("GET CMD responseLHM = \n" + requestParms.toString());
		return responseLHM;
	}

	// POST MAPPINGS

	@PostMapping(value = "/parmsTest", produces = "application/json")
	public Map<String, Object> parmsPostTest(@RequestParam LinkedHashMap<String, Object> requestParms,
			@RequestBody LinkedHashMap<String, Object> requestBody) {
		
		System.out.println("EXECUTING PARMS TEST = " + requestParms.toString());
		System.out.println("EXECUTING BODY TEST = " + requestBody.toString());
		LinkedHashMap<String, Object> responseParms = SystemCalls.mergeAndSetUpperCaseKeys(requestParms, requestBody);

		LinkedHashMap<String, Object> resultsMap = new LinkedHashMap<String, Object>();
		resultsMap.put("REQUEST PARMS", requestParms);
		resultsMap.put("REQUEST BODY", requestParms);
		
		resultsMap.put("RESPONSE PARMS", responseParms);

		System.out.println("GET CMD responseParms = \n" + responseParms.toString());
		return resultsMap;
	}

	@PostMapping(value = "/system", produces = "application/json")
	public Map<String, Object> systemPost(@RequestParam LinkedHashMap<String, Object> requestParms,
			@RequestBody LinkedHashMap<String, Object> requestBody) {

		System.out.println("EXECUTING PARMS TEST = " + requestParms.toString());
		System.out.println("EXECUTING BODY TEST = " + requestBody.toString());
		LinkedHashMap<String, Object> responseParms = SystemCalls.mergeAndSetUpperCaseKeys(requestParms, requestBody);

		LinkedHashMap<String, Object> resultsMap = new LinkedHashMap<String, Object>();
		resultsMap.put("REQUEST PARMS", requestParms);
		resultsMap.put("REQUEST BODY", requestParms);
		
		resultsMap.put("RESPONSE PARMS", responseParms);

		System.out.println("GET CMD responseParms = \n" + responseParms.toString());
		return resultsMap;
	}
	
}

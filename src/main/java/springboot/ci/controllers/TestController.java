package springboot.ci.controllers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.ci.services.SystemCallServices;
import springboot.ci.services.TestService;

@CrossOrigin("*")
@RestController
public class TestController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public TestService greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new TestService(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
	@GetMapping(value = "/parmsTest", produces = "application/json")
	public Map<String, Object> parmsGetTest(@RequestParam LinkedHashMap<String, Object> requestParms) {
		LinkedHashMap<String, Object> responseLHM = SystemCallServices.getResponseLHM("/parmsTest", "Get", requestParms);
		return responseLHM;
	}
	
	// POST MAPPINGS

	@PostMapping(value = "/parmsTest", produces = "application/json")
	public Map<String, Object> parmsPostTest(@RequestParam LinkedHashMap<String, Object> requestParms,
			@RequestBody LinkedHashMap<String, Object> requestBody) {
		
		LinkedHashMap<String, Object> responseLHM = SystemCallServices.postResponseLHM("/parmsTest", "Get", requestParms, requestBody);
		return requestBody;
//		return responseLHM;
	}

}

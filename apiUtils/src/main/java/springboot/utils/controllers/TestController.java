package springboot.utils.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.utils.services.TestService;

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
}

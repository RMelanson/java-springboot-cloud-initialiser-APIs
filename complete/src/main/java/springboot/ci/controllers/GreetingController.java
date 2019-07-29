package springboot.ci.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.ci.services.GreetingService;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @CrossOrigin()
    @RequestMapping("/greeting")
    public GreetingService greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new GreetingService(counter.incrementAndGet(),
                            String.format(template, name));
    }
}

package springboot.ci.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AliveTestController {
    @RequestMapping("/")
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name", name);
        String aliveResp = "<H1>Cloud Initialiser API's</H1>";
        aliveResp += "<a href='http://www.stockwidgets.com:9090/greeting'>Greeting Test</a><BR>";
        aliveResp += "<a href='http://www.stockwidgets.com:9090/systest'>System Call Test</a><BR>";
        return aliveResp;
    }
}

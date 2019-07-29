package springboot.ci.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AliveTestController {
	static String protocol = "HTTP";
	static String hostName = "www.stockwidgets.com";
	static String port     = "9090";
	static String baseURL  = protocol + hostName + port;

    @CrossOrigin()
    @RequestMapping("/")
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name", name);
        String aliveResp = "<H1>Cloud Initialiser API's</H1>";
        aliveResp += buildHref("greeting", "Greeting Test" );
        aliveResp += "<BR>";
        aliveResp += buildHref("system", "System Call Test" );
        aliveResp += "  Protocol: <input type=\"text\" name=\"Protocol\" value=\""+protocol+"\">";
        aliveResp += "://";
        aliveResp += "Port: <input type=\"text\" name=\"Port\" value=\""+hostName+"\">";
        aliveResp += port;
     	aliveResp += "Host: <input type=\"text\" name=\"Host\" value=\""+port+"\">"; 
        aliveResp += "<BR>";
//        aliveResp += "<a href='"+baseURL+"/greeting'>Greeting Test</a><BR>";
//        aliveResp += "<a href='"+baseURL+"/system'>System Call Test</a><BR>";
        return aliveResp;
    }
    
    String buildHref(String urlMapping, String txt){
    	String href = "";
    	href += "<a href=\"/";
      	href += protocol;
      	href += "://";
      	href += port;
      	href += ":";
      	href += hostName;
        href += "/\">";
    	href += txt;
      	href += "</a>";
		return href;	
    }

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
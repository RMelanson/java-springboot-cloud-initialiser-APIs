package springboot.ci.services;

import java.util.Arrays;

public class JUNK_SPLIT_TEST {

	public static void main(String[] args) {
		String request = "http://www.cloudinitializer.com:9090/install?app=wordpress&siteName=MyNewSite&port=9111&adminId=MyId&adminPWD=password&installDir=/var";
        System.out.println("request = "+request);
        
		String parts = request.replace("?", "&");
		String[] cmdLineParts = parts.split("&");
		String apiURL = cmdLineParts[0];
		String app = cmdLineParts[1];
		
		String[] parms = Arrays.copyOfRange(cmdLineParts, 2, cmdLineParts.length);
		
		System.out.println("cmdLineParts = "+cmdLineParts.toString());

	}

}

package springboot.ci.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

public class SystemCalls {

	static String osName = System.getProperty("os.name");
	static boolean isWindows = osName.toLowerCase().startsWith("windows");

	static final String CMD = "CMD";
	static final String CMD_RESP = "CMD responseLHM";

	public static Map<String, Object> sysCmd(LinkedHashMap<String, Object> lhm) {
		return lhm;
	}

	public static LinkedHashMap<String, Object> execGet(LinkedHashMap<String, Object> requestLHM) {
		LinkedHashMap<String, Object> responseLHM = new LinkedHashMap<String, Object>();
		execSysCmd(requestLHM,responseLHM);
		return responseLHM;
	}
	
	public static LinkedHashMap<String, Object> execPost(LinkedHashMap<String, Object> requestLHM) {
		LinkedHashMap<String, Object> responseLHM = new LinkedHashMap<String, Object>();
		execSysCmd(requestLHM,responseLHM);
		return responseLHM;
	}

    private static LinkedHashMap<String, Object> execSysCmd(LinkedHashMap<String, Object> requestLHM,
    		LinkedHashMap<String, Object> responseLHM) {
		Runtime r = Runtime.getRuntime();
		String cmd = (String) requestLHM.get(CMD);
		StringBuilder cmdBufferResp = new StringBuilder();
		LinkedHashMap<String, Object> replyLHM = new LinkedHashMap<String, Object>();
		replyLHM.put("REQUEST", requestLHM);
		replyLHM.put("responseLHM", responseLHM);
		responseLHM.put("OS", osName);
		responseLHM.put(CMD, cmd);

		if (StringUtils.isEmpty(cmd)) {
			responseLHM = new LinkedHashMap<String, Object>();
			responseLHM.put(CMD, "NULL");
			responseLHM.put(CMD_RESP, "ERR: Please Enter Valid System Command");
		} else
			try {
				/*
				 * Here we are executing the System Command. Returning the result as a String
				 */
				Process p = r.exec(cmd);
				InputStream in = p.getInputStream();
				BufferedInputStream buf = new BufferedInputStream(in);
				InputStreamReader inread = new InputStreamReader(buf);
				BufferedReader bufferedreader = new BufferedReader(inread);

				// Read the ls output
				String line;
				while ((line = bufferedreader.readLine()) != null) {
					cmdBufferResp.append(line);
				}
				responseLHM.put(CMD_RESP, cmdBufferResp.toString());
				System.out.println(line);
				// Check for ls failure
				try {
					if (p.waitFor() != 0) {
						responseLHM.put("ERR: waitFor()", p.exitValue());
					}
				} catch (InterruptedException e) {
					responseLHM.put("ERR: InterruptedException", e.toString());
					System.err.println(e);
				} finally {
					// Close the InputStream
					bufferedreader.close();
					inread.close();
					buf.close();
					in.close();
				}
			} catch (IOException e) {
				responseLHM.put("ERROR IOException", e.toString());
			}
		return replyLHM;
	}

	public static LinkedHashMap<String, Object> mergeAndSetUpperCaseKeys(LinkedHashMap<String, Object> requestParms,
			LinkedHashMap<String, Object> requestBody) {
		LinkedHashMap<String, Object> responseParms = new LinkedHashMap<String, Object>();
		for (String key : requestParms.keySet()) {
			Object value = requestParms.get(key);
			responseParms.put(key,value);
		}
		for (String key : requestBody.keySet()) {
			Object value = requestBody.get(key);
			responseParms.put(key,value);
		}
		setUpperCaseKeys(responseParms);
		return responseParms;
	}

	public static void setUpperCaseKeys(Map<String, Object> mp){
		Set<String> keySet = new HashSet<String>(mp.keySet());
		for (String key : keySet) {
			Object value = mp.get(key);
			mp.remove(key);
			mp.put(key.toUpperCase(),value);
		}
	}

}

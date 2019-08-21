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

	static final String CMD = "cmd";
	static final String DATA = "DATA";
	static final String ERROR = "ERROR";
	static final String RESPONSE = "RESPONSE";

	public static Map<String, Object> sysCmd(LinkedHashMap<String, Object> lhm) {
		return lhm;
	}

	private static LinkedHashMap<Integer, Object> execSysCmd(LinkedHashMap<String, Object> buildParms) {
		Runtime r = Runtime.getRuntime();
		String cmd = (String) buildParms.get(CMD);
//		StringBuilder cmdBufferResp = new StringBuilder();
//		Linked
		LinkedHashMap<Integer, Object> resultDataMap = new LinkedHashMap<Integer, Object>();

		if (StringUtils.isEmpty(cmd)) {
			buildParms = new LinkedHashMap<String, Object>();
			buildParms.put(ERROR, "Empty System Command");
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
				Integer lnCnt=0;
				while ((line = bufferedreader.readLine()) != null) {
					resultDataMap.put(++lnCnt, line);
				}
				System.out.println(line);
				// Check for ls failure
				try {
					if (p.waitFor() != 0) {
						buildParms.put("ERR: waitFor()", p.exitValue());
					}
				} catch (InterruptedException e) {
					buildParms.put("ERR: InterruptedException", e.toString());
					System.err.println(e);
				} finally {
					// Close the InputStream
					bufferedreader.close();
					inread.close();
					buf.close();
					in.close();
				}
			} catch (IOException e) {
				buildParms.put("ERROR IOException", e.toString());
			}
		return resultDataMap;
	}

	public static LinkedHashMap<String, Object> getResponseLHM(String api, String method,
			LinkedHashMap<String, Object> requestParms) {
		return postResponseLHM(api, method, requestParms, null);
	}

	public static LinkedHashMap<String, Object> postResponseLHM(String api, String method,
			LinkedHashMap<String, Object> requestParms, LinkedHashMap<String, Object> requestBody) {
		long startMillis = System.currentTimeMillis();

		System.out.println("Executing " + api + "API " + method + " Method" + "\nrequestParms : " + requestParms
				+ "\nrequestBody : " + (requestBody == null ? "null" : requestBody));

		LinkedHashMap<String, Object> buildParms = getNewMergedBodyParms(requestParms, requestBody);
		LinkedHashMap<String, Object> requestLHM = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> responseLHM = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> metaData = new LinkedHashMap<String, Object>();

		requestLHM.put("API", api);
		requestLHM.put("METHOD", method);
		requestLHM.put("PARMS", requestParms);
		responseLHM.put("REQUEST", requestLHM);
		responseLHM.put("META", metaData);
		metaData.put("SERVER OS", osName);
		if (!StringUtils.isEmpty(requestBody)) {
			requestLHM.put("BODY", requestBody);
		}
		// Start API Processing
		responseLHM.put(RESPONSE, SystemCalls.execSysCmd(buildParms));

		// END API Processing

		long elapseTimeMS = System.currentTimeMillis() - startMillis;
		metaData.put("PROCESSING TIME (MS)", elapseTimeMS);

		System.out.println("ResponseLHM = \n" + responseLHM.toString());
		return responseLHM;
	}

	public static LinkedHashMap<String, Object> getNewMergedBodyParms(LinkedHashMap<String, Object> requestParms,
			LinkedHashMap<String, Object> requestBody) {
		LinkedHashMap<String, Object> buildParms = new LinkedHashMap<String, Object>();

		if (requestParms != null)
			for (String key : requestParms.keySet()) {
				Object value = requestParms.get(key);
				buildParms.put(key, value);
			}

		if (requestBody != null)
			for (String key : requestBody.keySet()) {
				Object value = requestBody.get(key);
				buildParms.put(key, value);
			}
		return buildParms;
	}

	public static void setUpperCaseKeys(Map<String, Object> mp) {
		Set<String> keySet = new HashSet<String>(mp.keySet());
		for (String key : keySet) {
			Object value = mp.get(key);
			mp.remove(key);
			mp.put(key.toUpperCase(), value);
		}
	}
}

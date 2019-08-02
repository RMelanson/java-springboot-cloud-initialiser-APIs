package springboot.ci.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

public class SystemCalls {

	static String osName = System.getProperty("os.name");
	static boolean isWindows = osName.toLowerCase().startsWith("windows");

	static final String CMD = "SYSTEM CMD";
	static final String CMD_RESP = "CMD RESPONSE";

	public static Map<String, Object> sysCmd(LinkedHashMap<String, Object> lhm) {
		return lhm;
	}

	public static LinkedHashMap<String, Object> execSysCmd(LinkedHashMap<String, Object> requestLHM) {
		Runtime r = Runtime.getRuntime();
		String cmd = (String) requestLHM.get(CMD);
		StringBuilder cmdBufferResp = new StringBuilder();
		LinkedHashMap<String, Object> responceLHM = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> replyLHM = new LinkedHashMap<String, Object>();
		replyLHM.put("REQUEST", requestLHM);
		replyLHM.put("RESPONSE", responceLHM);
		responceLHM.put("OS", osName);
		responceLHM.put(CMD, cmd);

		if (StringUtils.isEmpty(cmd)) {
			responceLHM = new LinkedHashMap<String, Object>();
			responceLHM.put(CMD, "NULL");
			responceLHM.put(CMD_RESP, "ERR: Please Enter Valid System Command");
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
				responceLHM.put(CMD_RESP, cmdBufferResp.toString());
				System.out.println(line);
				// Check for ls failure
				try {
					if (p.waitFor() != 0) {
						responceLHM.put("ERR: waitFor()", p.exitValue());
					}
				} catch (InterruptedException e) {
					responceLHM.put("ERR: InterruptedException", e.toString());
					System.err.println(e);
				} finally {
					// Close the InputStream
					bufferedreader.close();
					inread.close();
					buf.close();
					in.close();
				}
			} catch (IOException e) {
				responceLHM.put("ERROR IOException", e.toString());
			}
		return replyLHM;
	}
}

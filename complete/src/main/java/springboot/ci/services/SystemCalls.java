package springboot.ci.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class SystemCalls {
	
	static String osName = System.getProperty("os.name");

	static boolean isWindows = osName.toLowerCase().startsWith("windows");
	
	public static Map<String,Object> test() {
		LinkedHashMap<String,Object> lhm = new LinkedHashMap<String,Object>();
		String cmd = "";
		if (isWindows)
			cmd="DIR";
		else
			cmd="ls -l";
		
		lhm.put("CMD", cmd);
		lhm.put("OS", osName);
		test(lhm);
		System.out.println(lhm);
		return lhm;
	}
		
	public static LinkedHashMap<String, Object> test(LinkedHashMap<String,Object> lhm) {
		Runtime r = Runtime.getRuntime();
		String cmd = (String) lhm.get("CMD");
		StringBuilder cmdBufferResp = new StringBuilder();

		try {
			/*
			 * Here we are executing the UNIX command ls for directory listing. The format
			 * returned is the long format which includes file information and permissions.
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
			lhm.put("CMD RESPONSE", cmdBufferResp.toString());
			System.out.println(line);
			// Check for ls failure
			try {
				if (p.waitFor() != 0) {
					lhm.put("ERROR waitFor()", p.exitValue());
				}
			} catch (InterruptedException e) {
				lhm.put("ERROR InterruptedException", e.toString());
				System.err.println(e);
			} finally {
				// Close the InputStream
				bufferedreader.close();
				inread.close();
				buf.close();
				in.close();
			}
		} catch (IOException e) {
			lhm.put("ERROR IOException", e.toString());
		}
		return lhm;
	}
}

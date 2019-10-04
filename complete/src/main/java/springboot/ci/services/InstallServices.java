package springboot.ci.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

public class InstallServices {

	static String osName = System.getProperty("os.name");
	static boolean isWindows = osName.toLowerCase().startsWith("windows");

	static final String CMD = "cmd";
	static final String DATA = "DATA";
	static final String ERROR = "ERROR";
	static final String RESPONSE = "RESPONSE";
	static final String APP = "app";
	static final String GIT_PROTOCOL = "git";
	static final String GIT_DOMAIN = "github.com";
	static final String GIT_ACCOUNT = "RMelanson";
	static final String GIT_FRMT = "%s@%s:%s/%s.git";
	static final String CI_DIR = "/opt/CI";
	static final String CI_BOOTSTRAP_DIR = CI_DIR + "/bootstraps";

	public static Map<String, Object> sysCmd(LinkedHashMap<String, Object> lhm) {
		return lhm;
	}

	private static boolean isValid(String app) {
		if (StringUtils.isEmpty(app))
			return false;
		return true;
	}

	public static Map<String, String> appRepoMap = new HashMap<String, String>();
	static {
		// APPS
		appRepoMap.put("WORDPRESS", "linux-scripts-apps-wordpress");
		appRepoMap.put("LINUX-SCRIPTS-APPS-WORDPRESS", "linux-scripts-apps-wordpress");
		// SERVERS
		appRepoMap.put("HTTP", "linux-scripts-server-http");
		appRepoMap.put("LINUX-SCRIPTS-APPS-HTTP", "linux-scripts-server-http");
		appRepoMap.put("JBOSS", "linux-scripts-server-jboss");
		appRepoMap.put("LINUX-SCRIPTS-APPS-JBOSS", "linux-scripts-server-jboss");
		// DB
		appRepoMap.put("ORACLE", "linux-scripts-db-oracle");
		appRepoMap.put("LINUX-SCRIPTS-APPS-ORACLE", "linux-scripts-db-oracle");
		appRepoMap.put("MYSQL", "linux-scripts-db-mysql");
		appRepoMap.put("LINUX-SCRIPTS-APPS-MYSQL", "linux-scripts-apps-mysql");
		appRepoMap.put("MONGODB", "linux-scripts-db-mongodb");
		appRepoMap.put("LINUX-SCRIPTS-APPS-MONGODB", "linux-scripts-apps-mongodb");
		appRepoMap.put("DB2", "linux-scripts-db-db2");
		appRepoMap.put("LINUX-SCRIPTS-APPS-DB2", "linux-scripts-db-db2");
		// DEVELOPMENT LANGUAGES
		appRepoMap.put("JAVA", "linux-scripts-dev-lang-java");
		appRepoMap.put("LINUX-SCRIPTS-DEV-LANG-JAVA", "linux-scripts-dev-lang-java");
		appRepoMap.put("NODEJS", "linux-scripts-dev-lang-nodejs");
		appRepoMap.put("LINUX-SCRIPTS-DEV-LANG-NODEJS", "linux-scripts-dev-lang-nodejs");
		appRepoMap.put("PHP", "linux-scripts-dev-lang-php");
		appRepoMap.put("LINUX-SCRIPTS-DEV-LANG-PHP", "linux-scripts-dev-lang-php");
		appRepoMap.put("PYTHON", "linux-scripts-dev-lang-python");
		appRepoMap.put("LINUX-SCRIPTS-DEV-LANG-PYTHON", "linux-scripts-dev-lang-python");
		appRepoMap.put("C++", "linux-scripts-dev-lang-cpp");
		appRepoMap.put("CPP", "linux-scripts-dev-lang-cpp");
		appRepoMap.put("LINUX-SCRIPTS-DEV-LANG-CPP", "linux-scripts-dev-lang-cpp");
		// TOOLS
		appRepoMap.put("MAVEN", "linux-scripts-tools-maven");
		appRepoMap.put("LINUX-SCRIPTS-TOOLS-MAVEN", "linux-scripts-tools-maven");

		appRepoMap.put("DOCKER", "linux-scripts-devops-docker");
		appRepoMap.put("LINUX-SCRIPTS-DEVOPS-DOCKER", "linux-scripts-devops-docker");

		appRepoMap.put("JENKINS", "linux-scripts-devops-jenkins");
		appRepoMap.put("LINUX-SCRIPTS-DEVOPS-JENKINS", "linux-scripts-devops-jenkins");

		appRepoMap.put("GIT", "linux-scripts-devops-git");
		appRepoMap.put("LINUX-SCRIPTS-DEVOPS-GIT", "linux-scripts-devops-git");

		appRepoMap.put("LINUX-ADMIN", "linux-scripts-devops-linux-admin");
		appRepoMap.put("LINUX-SCRIPTS-DEVOPS-LINUX-ADMIN", "linux-scripts-devops-linux-admin");

		appRepoMap.put("STORAGE-S3SF", "linux-scripts-aws-storage-s3sf");
		appRepoMap.put("LINUX-SCRIPTS-DEVOPS-STORAGE-S3SF", "linux-scripts-aws-storage-s3sf");

		appRepoMap.put("TEST", "linux-scripts-test");
		appRepoMap.put("LINUX-SCRIPTS-TEST", "linux-scripts-test");
	}

	private static String gitCloneURL(String app) {
		if (!isValid(app))
			return null;

		String appRepo = appRepoMap.get(app.toUpperCase());
		if (!isValid(appRepo))
			return null;

		String gitRepo = String.format(GIT_FRMT, GIT_PROTOCOL, GIT_DOMAIN, GIT_ACCOUNT, appRepo);
		return gitRepo;
	}

	private static String gitBootstrapInstallDir(String app) {
		if (!isValid(app))
			return null;

		String appRepo = appRepoMap.get(app.toUpperCase());
		if (!isValid(appRepo))
			return null;
		appRepo = appRepo.replace("linux-scripts-", "/").replace("-", "/");

		String gitRepo = CI_BOOTSTRAP_DIR + appRepo;
		return gitRepo;
	}

	private static String execSysCmd(String cmd) {
		String installresponse = "";
		System.out.println("CMD = " + cmd);

		if (isValid(cmd)) {
			{
				/*
				 * Here we are executing the System Command. Returning the result as a String
				 */
				try {
					Runtime r = Runtime.getRuntime();
					Process p = r.exec(cmd);
					InputStream in = p.getInputStream();
					BufferedInputStream buf = new BufferedInputStream(in);
					InputStreamReader inread = new InputStreamReader(buf);
					BufferedReader bufferedreader = new BufferedReader(inread);

					installresponse = IOUtils.toString(bufferedreader);

					System.out.println(installresponse);
					// Check for cmd failure
					try {
						if (p.waitFor() != 0) {
							installresponse = "*ERR: waitFor()" + p.exitValue();
						}
					} catch (InterruptedException e) {
						installresponse = "*ERR: InterruptedException" + e.toString();
						System.err.println(e);
					} finally {
						// Close the InputStream
						bufferedreader.close();
						inread.close();
						buf.close();
						in.close();
					}
				} catch (IOException e) {
					installresponse = "*ERR IOException" + e.toString();
				}
			}
		} else
			installresponse = "*ERR Application not available for installation";
		return installresponse;
	}

	private static String installApp(String app, LinkedHashMap<String, Object> buildParms) {
		String installresponse = "";
		String parms = buildParms.toString().replace("{", "").replace("}", "").replace(",","");
		String gitRepo = gitCloneURL(app);
		String shellScript = "./"+app+".sh " +parms;
//		if (cmdLineParts.length > 1) {
//			installresponse = cloneApp(app);
//			if (installresponse.length() > 1)
//				return installresponse;
//			else {
//				String parms = "";
//				if (cmdLineParts.length > 2) {
//					String[] parmsArr = Arrays.copyOfRange(cmdLineParts, 2, cmdLineParts.length);
//					 for (Object parm : parmsArr) { 
//						 parms += " "+(String)parm; 
//				        } 
//					 runSetup(app+parms);
//				}
//			}
//		}
		installresponse = shellScript;
		return installresponse;
	}

	private static void runSetup(String setupCmd) {
		// TODO Auto-generated method stub
		System.out.println("RUNNING setupCmd = " + setupCmd);
	}

	private static String cloneApp(String app) {
		String installresponse = "";

		String cloneURL = gitCloneURL(app);

		if (isValid(cloneURL)) {
			String bootstrapInstallDir = gitBootstrapInstallDir(app);
			String gitCloneCmd = "git clone " + cloneURL + " " + bootstrapInstallDir;

			/*
			 * Here we are executing the git Clone System Command. Returning the result as a
			 * String
			 */
			installresponse += execSysCmd(gitCloneCmd);
			if (StringUtils.isEmpty(installresponse))
				installresponse = "Installation Successfully installed in:" + bootstrapInstallDir;
		} else {
			installresponse = "**ERR** app = " + app + " Repository Not Found";
		}
		return installresponse;
	}

	public static LinkedHashMap<String, Object> getResponseLHM(String api, String app, String method,
			LinkedHashMap<String, Object> requestParms) {
		return postResponseLHM(api, app, method, requestParms, null);
	}

	public static LinkedHashMap<String, Object> postResponseLHM(String api, String app, String method,
			LinkedHashMap<String, Object> requestParms, LinkedHashMap<String, Object> requestBody) {
		long startMillis = System.currentTimeMillis();

		System.out.println("Executing " + api + "API " + api + "api " + method + " Method" + "\nrequestParms : " + requestParms
				+ "\nrequestBody : " + (requestBody == null ? "null" : requestBody));

		LinkedHashMap<String, Object> buildParms = getNewMergedBodyParms(requestParms, requestBody);
		LinkedHashMap<String, Object> requestLHM = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> responseLHM = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> metaData = new LinkedHashMap<String, Object>();

		requestLHM.put("API", api);
		requestLHM.put("APP", app);
		requestLHM.put("METHOD", method);
		requestLHM.put("PARMS", requestParms);
		responseLHM.put("REQUEST", requestLHM);
		responseLHM.put("META", metaData);
		metaData.put("SERVER OS", osName);
		if (!StringUtils.isEmpty(requestBody)) {
			requestLHM.put("BODY", requestBody);
		}
		// Start API Processing
		responseLHM.put(RESPONSE, InstallServices.installApp(app, buildParms));

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

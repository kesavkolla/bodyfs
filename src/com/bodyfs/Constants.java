package com.bodyfs;

import java.util.HashMap;

public class Constants {

	public static final String SESSION_LOGIN_CRED = "LOGIN_CREDENTIALS";
	public static final String SESSION_PERSON_TYPE = "PERSON_TYPE";
	
	 public static final HashMap<String, String>diagnosticCodes = new HashMap<String, String>();
	 
	 static {
		 diagnosticCodes.put("719.49", "Pain in Joint, Multiple Sites");
		 diagnosticCodes.put("723.1", "Pain in Neck");
		 diagnosticCodes.put("724.5", "Pain in Back (postural)");
		 diagnosticCodes.put("789", "Pain Abdominal (unspecified sites)");
		 diagnosticCodes.put("724.2", "Pain, lumbar region");
	 }
}

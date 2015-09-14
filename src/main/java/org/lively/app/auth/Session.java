package org.lively.app.auth;

import sun.misc.*;

import java.security.*;
import java.util.*;

import org.lively.app.datatypes.TempCollection;

public class Session {
	
	public static String newSessionId(String user) {
		SecureRandom gen = new SecureRandom();
		byte randomBytes[] = new byte[32];
		gen.nextBytes(randomBytes);
		
		BASE64Encoder encoder = new BASE64Encoder();
		
		String sessionId = encoder.encode(randomBytes);
		
		//make sure to add the session id to the temp collection
		//collection.getSessions().put(user, sessionId);
		
		return sessionId.toString();
	}
	
	/**
	public void endSession(String sessionId) {
		session.remove(sessionId);
	}
	
	public String getSessionId(String user) {
		return session.get(user).toString();
	}
	**/
	
} 


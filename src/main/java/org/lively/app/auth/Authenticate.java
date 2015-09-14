package org.lively.app.auth;

import static spark.Spark.*;

import java.util.*;

import spark.Request;


public class Authenticate {
	
	/*public boolean authenticateUser(String user, String pass){
		String authPass = credentials.get(user);
		if (!(pass != null && pass.equals(authPass))){
			return false;
		} else { return true; }
	}*/
		
	public static String getUser(Request request){
		String userName = request.queryParams("user");
		return userName;
	}	
	
	public static String getPass(Request request){
		String password = request.queryParams("password");
		return password;
	}

}


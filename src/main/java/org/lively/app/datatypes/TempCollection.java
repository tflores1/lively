package org.lively.app.datatypes;

import java.security.SecureRandom;
import java.util.*;

import org.lively.app.auth.Session;
import org.lively.app.auth.User;

public class TempCollection {
	
	private Random random = new SecureRandom();
	
	private static Map<Attribute, Attribute> collection = new HashMap<Attribute, Attribute>();
	private static Attribute users;
	private static Attribute sessions;
	
	/**
	 * constructor for the temp collection
	 */
	public TempCollection() {
		users = new Attribute();
		users.setName("userCollection");
		
		sessions = new Attribute();
		sessions.setName("sessionCollection");
		
		collection.put(users, getSessions());
	}
	
	public Map<Attribute, Attribute> getCollection() {
		return collection;
	}
	
	/**
	 * adds a new user to the temp collection
	 * @param username
	 * @param pass
	 */
	public boolean addUser(String username, String pass) {
		boolean check = validateNewUser(username);
		if (!check){
			return false;
		}
		
		String passwordHash = User.createPasswordHash(pass, Integer.toString(random.nextInt()));
		users.getMap().put(username, passwordHash);
		
		System.out.println(users.getMap().get(username));
		return true;
	}
	
	/**
	 * generates a new session Id and adds the 
	 * username and session key to the temp collection
	 * @param username
	 */
	public void addSession(String username) {
		String sessionId = Session.newSessionId(username);
		sessions.getMap().put(username, sessionId);
		sessions.getMap().put(sessionId, username);
	}
	
	/**
	 * checks to see if the username is taken
	 * @return
	 */
	public boolean validateNewUser(String username) {
		if (!users.getMap().containsValue(username)){
			return true;
		} else { return false; }
	}
	
	/**
	 * returns the sessions collection
	 * can implement hashmap methods on return, 
	 * or getMap();
	 * @return
	 */
	public Attribute getSessions() {
		return sessions;
	}
	
	/**
	 * returns the users collection
	 * can implement hashmap methods on return, 
	 * or getMap();
	 * @return
	 */
	public Attribute getUsers() {
		return users;
	}
	
	/**
	 * ends a users session
	 * @param sessionId
	 */
	public void endSession(String sessionId) {
		sessions.getMap().remove(sessionId);
	}
	
	/**
	 * returns the session id associated with a user
	 * @param username
	 * @return
	 */
	public String getSessionId(String username) {
		return sessions.getMap().get(username);
	}
	
	/**
	 * returns the userid based on the session id
	 * @param sessionId
	 * @return
	 */
	public String getUserBySessionId(String sessionId) {
		String username = sessions.getMap().get(sessionId);
		
		System.out.println(username);
		
		if (username == null){
			return null;
		} else { return username; }
	}
	
	/**
	 * Validates a users login credentials
	 * @param user
	 * @param pass
	 * @return
	 */
	public boolean validateLogin(String user, String pass){
		Boolean userExists = users.getMap().containsKey(user);
		if (!userExists) { 
			System.out.println("Oops, user does not exist");
			return false; 
		}
		
		String authPass = users.getMap().get(user);
				
		String salt = authPass.split(",")[1];
		
		if (!(pass != null && pass.equals(User.createPasswordHash(authPass, salt)))){
			System.out.println("Password does not seem to be a match");
			return false;
		} else { return true; }
	}
	
}
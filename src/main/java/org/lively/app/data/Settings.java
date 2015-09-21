package org.lively.app.data;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.lively.app.auth.User;

import sun.misc.BASE64Encoder;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;



public class Settings {

	private static DBCollection userSettings;
	private Random random = new SecureRandom();
	
	public Settings(final DB livelyDatabase) {
		userSettings = livelyDatabase.getCollection("users");
	}
	
	public Settings(final DB livelyDatabase, String username) {
		userSettings = livelyDatabase.getCollection("users");
		
	}
	
	public String getUsername(DBObject user, String username) {
		String name = user.get("_id").toString();
		return name;
	}
	
	public boolean updatePassword(DBObject user, String username, String newPassword) {
		String newPassHash = makePasswordHash(newPassword, Integer.toString(random.nextInt()));
		
		BasicDBObject userQuery = new BasicDBObject().append("_id", username);
		
		BasicDBObject setPass = new BasicDBObject();
		setPass.append("$set", new BasicDBObject().append("password", newPassHash));
		
		userSettings.update(userQuery, setPass);
		
		String hashedAndSalted = user.get("password").toString();

        String salt = hashedAndSalted.split(",")[1];

        if (!hashedAndSalted.equals(makePasswordHash(newPassword, salt))) {
            System.out.println("We did not update correctly");
            return false;
        } else { return true; }
	}
	
	public String getEmail(DBObject user, String username) {
		String email = user.get("email").toString();
		return email;
	}
	
	@SuppressWarnings("restriction")
	private String makePasswordHash(String password, String salt) {
        try {
            String saltedAndHashed = password + "," + salt;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(saltedAndHashed.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            byte hashedBytes[] = (new String(digest.digest(), "UTF-8")).getBytes();
            return encoder.encode(hashedBytes) + "," + salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 is not available", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 unavailable?  Not a chance", e);
        }
    }
		
	
}
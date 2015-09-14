package org.lively.app.auth;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import sun.misc.*;

public class User {
	
	//private Random random = new SecureRandom();
	
	public static String createPasswordHash(String password, String salt) {
		try {
			String saltHash = password + "," + salt;
			MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(saltHash.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            byte hashedBytes[] = (new String(digest.digest(), "UTF-8")).getBytes();
            return encoder.encode(hashedBytes) + "," + salt;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 is not available", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 unavailable", e);
		}
	}
}
package org.lively.app.auth;

import sun.misc.*;

import java.security.*;
import java.util.*;

import org.lively.app.datatypes.TempCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class Session {
	
	private final DBCollection sessionsCollection;

    public Session(final DB blogDatabase) {
        sessionsCollection = blogDatabase.getCollection("sessions");
    }

    public String findUserNameBySessionId(String sessionId) {
        DBObject session = getSession(sessionId);

        if (session == null) {
            return null;
        }
        else {
            return session.get("username").toString();
        }
    }

    // starts a new session in the sessions table
    @SuppressWarnings("restriction")
	public String startSession(String username) {

        // get 32 byte random number. that's a lot of bits.
        SecureRandom generator = new SecureRandom();
        byte randomBytes[] = new byte[32];
        generator.nextBytes(randomBytes);

        BASE64Encoder encoder = new BASE64Encoder();
        String sessionID = encoder.encode(randomBytes);

        // build the BSON object
        BasicDBObject session = new BasicDBObject("username", username);
        session.append("_id", sessionID);
        sessionsCollection.insert(session);
        return session.getString("_id");
    }

    // ends the session by deleting it from the sesisons table
    public void endSession(String sessionID) {
        sessionsCollection.remove(new BasicDBObject("_id", sessionID));
    }

    // retrieves the session from the sessions table
    public DBObject getSession(String sessionID) {
        return sessionsCollection.findOne(new BasicDBObject("_id", sessionID));
    }
}
package org.dneversky.idea.model;

public class User {

    private String sessionID;
    private String id;

    public User(String sessionID) {
        this.sessionID = sessionID;
    }

    public User(String sessionID, String id) {
        this.sessionID = sessionID;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}

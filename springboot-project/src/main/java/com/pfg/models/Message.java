package com.pfg.models;

public class Message {
    
    public User userM;

    public String message;

    public Message(User userM, String message) {
        this.userM = userM;
        this.message = message;
    }

    public Message() {
    }

    public User getUserM() {
        return userM;
    }

    public void setUserM(User userM) {
        this.userM = userM;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
}

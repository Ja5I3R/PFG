package com.pfg.models;

public class MessageRequest {
    
    public String user;

    public String message;

    public Long id;

    public MessageRequest(String user, String message, Long id) {
        this.user = user;
        this.message = message;
        this.id = id;
    }

    public MessageRequest() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}

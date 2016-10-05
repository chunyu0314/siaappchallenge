package com.rep5.sialah.common.models;

/**
 * Created by low on 5/10/16 2:53 PM.
 */
public class PlaneChatPacket {

    private String token;
    private String message;

    public PlaneChatPacket() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

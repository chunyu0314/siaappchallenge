package com.rep5.sialah.common.models;

/**
 * Created by low on 4/10/16 3:00 PM.
 */
public class StoreConvo {

    //stores previous 10 convo for customer service, jx to set this value
    //store from earliest to latest
    //clear after 1st contact to reduce payload
    //prepend each string with source of msg, use "usr-Whatever message" and "bot-Whatever message"
    private String[] lastTenConvo;

    public StoreConvo() {
    }

    public String[] getLastTenConvo() {
        return lastTenConvo;
    }

    public void setLastTenConvo(String[] lastTenConvo) {
        this.lastTenConvo = lastTenConvo;
    }
}

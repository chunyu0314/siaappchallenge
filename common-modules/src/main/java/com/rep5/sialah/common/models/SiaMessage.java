package com.rep5.sialah.common.models;

import java.io.Serializable;

/**
 * Created by chen0 on 26/5/2016.
 */
public class SiaMessage implements Serializable {


    private String message = "";
    private String data = "";

    public SiaMessage() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

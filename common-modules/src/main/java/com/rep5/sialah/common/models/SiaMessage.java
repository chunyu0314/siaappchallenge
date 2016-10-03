package com.rep5.sialah.common.models;

import java.io.Serializable;

/**
 * Created by chen0 on 26/5/2016.
 */
public class SiaMessage implements Serializable {

    private long id;
    private String message;
    private Context context;

    public SiaMessage() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

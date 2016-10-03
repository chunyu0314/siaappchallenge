package com.rep5.sialah.common.models.watson;

/**
 * Created by low on 3/10/16 9:16 AM.
 */
public class WatsonEntity {
    private String entity;
    private int[] location;
    private String value;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

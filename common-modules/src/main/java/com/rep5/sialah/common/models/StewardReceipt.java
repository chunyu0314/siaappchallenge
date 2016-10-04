package com.rep5.sialah.common.models;

/**
 * Created by low on 4/10/16 2:46 PM.
 */
public class StewardReceipt {
    int id;
    String requestType;
    String requestItem;
    String seatNumber;

    public StewardReceipt() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestItem() {
        return requestItem;
    }

    public void setRequestItem(String requestItem) {
        this.requestItem = requestItem;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}

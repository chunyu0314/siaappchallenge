package com.rep5.sialah.common.models.fcm;

import com.rep5.sialah.common.models.SiaMessage;

/**
 * Created by low on 3/10/16 5:43 AM.
 */
public class FcmPacket {
    private String to;
    private FcmNotif notification;
    private SiaMessage data;

    public FcmPacket() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public FcmNotif getNotification() {
        return notification;
    }

    public void setNotification(FcmNotif notification) {
        this.notification = notification;
    }

    public SiaMessage getData() {
        return data;
    }

    public void setData(SiaMessage data) {
        this.data = data;
    }
}


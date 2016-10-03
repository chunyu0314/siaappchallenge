package com.sialah.pushnotif.tests;

import com.rep5.sialah.common.models.Context;
import com.rep5.sialah.common.models.SiaData;
import com.rep5.sialah.common.models.SiaMessage;
import com.rep5.sialah.common.models.fcm.FcmNotif;
import com.rep5.sialah.common.models.fcm.FcmPacket;
import com.rep5.sialah.pushnotif.FCMImpl;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;

/**
 * Created by low on 3/10/16 6:13 PM.
 */
public class FCMTest {
    @Test
    @Ignore
    public void push() {
        FcmNotif notif = new FcmNotif();
        notif.setBody("TEST BODY");
        notif.setTitle("YOYO TITLE");

        SiaData data = new SiaData();
        data.setFakeBooking(true);
        data.setCustomerRequestType("Order food");

        Context context = new Context();
        context.setSiaData(data);

        SiaMessage message = new SiaMessage();
        message.setMessage("MESSAGE BODY");
        message.setId(1235);
        message.setContext(context);

        FcmPacket packet = new FcmPacket();
        packet.setNotification(notif);
        packet.setData(message);
        //TODO change setTo()
        packet.setTo("SET TO THE RECEIVER");

        FCMImpl.push(packet);
    }
}

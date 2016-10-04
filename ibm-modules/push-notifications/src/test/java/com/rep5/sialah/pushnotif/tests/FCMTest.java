package com.rep5.sialah.pushnotif.tests;

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
        //packet.setTo("/topics/sia");
        packet.setTo("cJdxbi_sHXQ:APA91bFxG7Te02f_zAiHGhNaVfFq_8GIubnxnYRiDSZhcDpIqOfc8zbD3CICDqN0mXp1L9aA4_sItYHITKkbKdBjSFW2kC6DNCP_1r8fJXyIhri9SW0KU7i4PPU-GwYfa6N028gUyT5b");

        FCMImpl.push(packet);
    }
}

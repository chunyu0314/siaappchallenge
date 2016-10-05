package com.rep5.sialah.webendpoint;

import com.rep5.sialah.common.CustomerData;
import com.rep5.sialah.common.models.Context;
import com.rep5.sialah.common.models.PlaneChatPacket;
import com.rep5.sialah.common.models.SiaData;
import com.rep5.sialah.common.models.SiaMessage;
import com.rep5.sialah.common.models.fcm.FcmNotif;
import com.rep5.sialah.common.models.fcm.FcmPacket;
import com.rep5.sialah.pushnotif.FCMImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by low on 5/10/16 3:04 PM.
 */
public class PlaneChat {
    private static Logger logger = LoggerFactory.getLogger(PlaneChat.class);

    public static void handlePacket(PlaneChatPacket packet) {
        SiaData data = new SiaData();
        data.setFakeBooking(false);
        data.setPlaneChat(true);

        Context context = new Context();
        context.setSiaData(data);

        SiaMessage message = new SiaMessage();
        message.setMessage(packet.getMessage());
        message.setContext(context);

        FcmNotif notif = new FcmNotif();
        notif.setBody(packet.getMessage());

        FcmPacket fcm = new FcmPacket();
        fcm.setData(message);

        if (CustomerData.getFirebaseToken() == null) {
            logger.error("Jie Xun's token not set");
            if (CustomerData.getFriendToken() == null) {
                logger.error("Friend's token not set");
            }
            return;
        }
        if (CustomerData.getFriendToken() == null) {
            logger.error("Friend's token not set");
            return;
        }

        if (packet.getToken().matches(CustomerData.getFirebaseToken())) {
            notif.setTitle("Jie Xun");
            fcm.setTo(CustomerData.getFriendToken());
        }
        else if (packet.getToken().matches(CustomerData.getFriendToken())) {
            notif.setTitle("Mummy");
            fcm.setTo(CustomerData.getFirebaseToken());
        }
        else {
            logger.error("WTF??!! How the heck I get this error");
            return;
        }

        fcm.setNotification(notif);

        FCMImpl.push(fcm);
    }
}

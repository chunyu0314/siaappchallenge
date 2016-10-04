package com.rep5.sialah.pushnotif;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rep5.sialah.common.CustomerData;
import com.rep5.sialah.common.RestClient;
import com.rep5.sialah.common.models.SiaMessage;
import com.rep5.sialah.common.models.fcm.FcmNotif;
import com.rep5.sialah.common.models.fcm.FcmPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by low on 3/10/16 9:55 AM.
 */
public class FCMImpl {
    private static final String url = "https://fcm.googleapis.com/fcm/send";

    private static final String apiKey = "AIzaSyBvCnFiw2tf8aVnMMjKEpJT7coCipMLLiE";
    //private static final String apiKey = "AIzaSyAlw5imiOSohUaNVnWi5O0a_pXOMvW4tpw";
    private static final Logger logger = LoggerFactory.getLogger(FCMImpl.class);

    public static void push(FcmPacket msg) {

        logger.info("sending to push notif: " + JSON.toJSONString(msg));

        Response response = RestClient.getTarget(url).request().header(HttpHeaders.AUTHORIZATION, "key=" + apiKey).post(Entity.entity(msg, MediaType.APPLICATION_JSON));
        if (response.getStatus()/100 != 2) {
            logger.error("failed to send push notif");
            logger.error("status code: " + response.getStatus());
            logger.error(JSON.toJSONString(response.readEntity(JSONObject.class)));
        }
        else {
            logger.info("sent push notif");
        }
    }

    public static void pushSiaMessage(SiaMessage message) {
        FcmPacket packet = null;
        try {
            packet = createFcmPacket(message);
        } catch (Exception e) {
            logger.error("no token found in customer data");
        }

        push(packet);
    }

    public static FcmPacket createFcmPacket(SiaMessage message) throws Exception {

        String token = CustomerData.getFirebaseToken();
        if (token == null) {
            throw new Exception("no token");
        }

        FcmNotif notif = new FcmNotif();
        notif.setBody(message.getMessage());
        notif.setTitle("Message from SIA Intelligent Assistant");

        FcmPacket packet = new FcmPacket();
        packet.setNotification(notif);
        packet.setData(message);
        packet.setTo(CustomerData.getFirebaseToken());

        return packet;
    }
}

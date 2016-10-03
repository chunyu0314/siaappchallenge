package com.rep5.sialah.pushnotif;

import com.rep5.sialah.common.RestClient;
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
    private static final String apiKey = "AIzaSyAlw5imiOSohUaNVnWi5O0a_pXOMvW4tpw";
    private static final Logger logger = LoggerFactory.getLogger(FCMImpl.class);

    public static void push(FcmPacket msg) {

        Response response = RestClient.getTarget(url).request().header(HttpHeaders.AUTHORIZATION, "key=" + apiKey).post(Entity.entity(msg, MediaType.APPLICATION_JSON));
        if (response.getStatus()/100 != 2) {
            logger.error("failed to send push notif");
            logger.error(response.getEntity().toString());
        }
        else {
            logger.info("sent push notif");
        }
    }
}
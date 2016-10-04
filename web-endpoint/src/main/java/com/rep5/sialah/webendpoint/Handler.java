package com.rep5.sialah.webendpoint;

import com.rep5.sialah.common.models.SiaMessage;
import com.rep5.sialah.convo.ConvoImpl;
import com.rep5.sialah.pushnotif.FCMImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by low on 2/10/16 4:07 PM.
 */
public class Handler {

    private static Logger logger = LoggerFactory.getLogger(Handler.class);

    public static void handleText(SiaMessage message) {
        logger.info("received message: " + message.getMessage());
        SiaMessage watsonReply = ConvoImpl.useConvo(message);
        FCMImpl.push(FCMImpl.createFcmPacket(watsonReply));

    }

    public static void handleSteward(SiaMessage message) {

    }

    public static void handleServiceReply(SiaMessage message) {

    }
}

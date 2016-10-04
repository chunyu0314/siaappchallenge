package com.rep5.sialah.webendpoint;

import com.rep5.sialah.common.ContextCache;
import com.rep5.sialah.common.CustomerData;
import com.rep5.sialah.common.models.*;
import com.rep5.sialah.common.models.fcm.FcmPacket;
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

        if (ContextCache.isTalkToCus()) {
            //TODO send to customer service
            return;
        }

        message.setContext(ContextCache.getContextAndReset());
        SiaMessage watsonReply = ConvoImpl.useConvo(message);
        if (watsonReply.getContext().getSiaData().getCustomerRequestItem() != null) {
            StewardReceipt msgToSteward = new StewardReceipt();
            msgToSteward.setId(ContextCache.getNewId());
            msgToSteward.setRequestType(watsonReply.getContext().getSiaData().getCustomerRequestType());
            msgToSteward.setRequestItem(watsonReply.getContext().getSiaData().getCustomerRequestItem());
            msgToSteward.setSeatNumber(CustomerData.getSeatNumber());
        }
        FCMImpl.push(FCMImpl.createFcmPacket(watsonReply));

    }

    public static void handleSteward(StewardReply message) {
        String item = ContextCache.getContext().getSiaData().getCustomerRequestItem();
        SiaMessage msg = new SiaMessage();
        msg.setContext(ContextCache.getContextAndReset());
        if (message.getStatus().matches(Constants.ACCEPTED)) {
            msg.setMessage("Your request have been accepted. The " + item + " will be served to you shortly.");
        }

        else if (message.getStatus().matches(Constants.POSTPONED)) {
            msg.setMessage("Sorry, our air stewardess are unable to attend tou you right now. We will be notifying you again when your request is ready.");
        }

        else if (message.getStatus().matches(Constants.REJECTED)) {
            msg.setMessage("Sorry, " + item + "s are no longer available, would you like to make another request?");
        }
        FCMImpl.push(FCMImpl.createFcmPacket(msg));

    }

    private static void doPush(String text) {

        SiaMessage msg = new SiaMessage();
        msg.setContext(ContextCache.getContextAndReset());
        msg.setMessage(text);
        FCMImpl.push(FCMImpl.createFcmPacket(msg));
    }

    public static void handleServiceReply(String reply) {

        if (!ContextCache.isTalkToCus()) {
            logger.error("Customer service talking while state is not set");
            return;
        }
        doPush(reply);

    }

    //TODO to handle messages to be sent to customer service
    public static void handleService(StoreConvo convo) {


    }

    public static void sendBaggage() {
        doPush("Your baggage have been loaded onto the plane");
    }

    public static void sendCheckin() {
        doPush("Your check in counter will be at blah blah blah");
    }

    public static void boardedPlane() {
        doPush("Hi, welcome on board, please connect to the on board wifi to continue using me!");
    }

    public static void sendSchedule() {
        doPush("This time off lights, this time get hot towel, blah blah");
    }

    public static void reachedDestination() {
        doPush("Welcome to San Francisco");
        doPush("Weather is good!!");
        doPush("you can get your sim card here");
        doPush("at the mean time connect to #SFO_FREE_WIFI");
    }


}

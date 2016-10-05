package com.rep5.sialah.webendpoint;

import com.rep5.sialah.common.ContextCache;
import com.rep5.sialah.common.ConvoStore;
import com.rep5.sialah.common.CustomerData;
import com.rep5.sialah.common.MessageStore;
import com.rep5.sialah.common.models.*;
import com.rep5.sialah.convo.ConvoImpl;
import com.rep5.sialah.pushnotif.FCMImpl;
import com.rep5.sialah.pushnotif.SendToSteward;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by low on 2/10/16 4:07 PM.
 */
public class Handler {

    private static Logger logger = LoggerFactory.getLogger(Handler.class);

    public static void handleText(String msg) {
        logger.info("received message: " + msg);

        if (ContextCache.isTalkToCus()) {
            MessageStore.setMessage(msg);
            return;
        }

        SiaMessage message = new SiaMessage();
        message.setMessage(msg);

        message.setContext(ContextCache.getContextAndReset());
        SiaMessage watsonReply = ConvoImpl.useConvo(message);
        //clear confirmingCheckIn flag
        ContextCache.getContext().getSiaData().setConfirmingCheckIn(false);
        if (watsonReply.getContext().getSiaData().getCustomerRequestType() != null) {
            StewardReceipt msgToSteward = new StewardReceipt();
            msgToSteward.setId(ContextCache.getNewId());
            msgToSteward.setRequestType(watsonReply.getContext().getSiaData().getCustomerRequestType());
            msgToSteward.setRequestItem(watsonReply.getContext().getSiaData().getCustomerRequestItem());
            msgToSteward.setSeatNumber(CustomerData.getSeatNumber());
            SendToSteward.send(msgToSteward);
        }
        FCMImpl.pushSiaMessage(watsonReply);

    }

    public static void handleSteward(String status) {
        String item = ContextCache.getContext().getSiaData().getCustomerRequestItem();
        String response = "default";
        if (status.matches(Constants.ACCEPTED)) {
            response = "Your request has been accepted. The " + item + " will be served to you shortly.";
        }

        else if (status.matches(Constants.POSTPONED)) {
            response = "Sorry, our air stewardess are unable to attend to you right now. We will be notifying you again when your request is ready.";
        }

        else if (status.matches(Constants.REJECTED)) {
            response = "Sorry, " + item + " is no longer available, would you like to make another request?";
        }
        doPush(response);

    }

    private static void doPush(String text) {

        SiaMessage msg = new SiaMessage();
        msg.setContext(ContextCache.getContextAndReset());
        msg.setMessage(text);
        FCMImpl.pushSiaMessage(msg);
    }

    public static void handleServiceReply(String reply) {

        if (!ContextCache.isTalkToCus()) {
            logger.error("Customer service talking while state is not set");
            return;
        }
        doPush(reply);

    }

    public static void handleService(ChatBotMessage[] convo) {
        ConvoStore.setMessages(convo);
    }

    public static void sendBaggage() {
        doPush("Hi there, glad to inform you that your baggage have been loaded onto the plane");
    }

    public static void sendCheckin() {
        doPush("Hope you are ready for your flight tonight! Your check in counter will be at Terminal 3 counter 10. It's recommended to check in by 6pm to avoid missing the flight.");
    }

    public static void boardedPlane() {
        doPush("Hi, welcome on board, please connect to the on board wifi to continue using me!");
    }

    public static void sendSchedule() {
        doPush("This time off lights, this time get hot towel, blah blah");
    }

    public static void reachedDestination() {
        doPush("Welcome to San Francisco");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doPush("Weather is good!!");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doPush("you can get your sim card here");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doPush("at the mean time connect to #SFO_FREE_WIFI");
    }


    public static void sendFirstMessage() {
        doPush("Hi, I am SIA, Sia Intelligent Assistant. You can ask me anything!");
    }

    public static void askForCheckIn() {
        doPush("A reminder that your flight is in 2 days time. Would you like to do an online check in now?");
    }
}

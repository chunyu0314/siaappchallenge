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

        ContextCache.getContext().getSiaData().setNeedsCustomerService(false);

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
        ContextCache.getContext().getSiaData().setIsCusService(true);
        doPush(reply);
        ContextCache.getContext().getSiaData().setIsCusService(false);

    }

    public static void handleService(ChatBotMessage[] convo) {
        ConvoStore.setMessages(convo);
    }

    public static void sendBaggage() {
        doPush("Hi there, glad to inform you that your baggage has been loaded onto the plane!");
    }

    public static void sendCheckin() {
        doPush("Hope you are ready for your flight tonight! Your check in counter will be at Terminal 3 counter 10." +
                " It's recommended to check in your luggage by 6pm to avoid missing the flight.");
    }

    public static void boardedPlane() {
        doPush("Hi, welcome on board SQ32, please connect to SQ32_WIFI to continue talking to me!");
    }

    public static void sendSchedule() {
        doPush("The plane is about to take off in 10 minutes. Hope you will find the following flight schedule useful!\n" +
                "------\n" +
                "8:10pm: Take off\n" +
                "8:40pm: KrisFlyer shop open\n" +
                "9:00pm: Dinner\n" +
                "11pm - 6am: Lights off\n" +
                "6:10am: Serving hot towels\n" +
                "8am: Breakfast\n" +
                "------\n" +
        "*all times shown in SGT (GMT +8)");
    }

    public static void reachedDestination() {
        doPush("Welcome to San Francisco! Weather here is sunny. Temperature right now is 16 degrees celsius. Do bring along a light jacket.");
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doPush("After clearing the immigration, you may proceed towards the left of the arrival hall " +
                "for the telecomm counters where you can purchase your sim card.");
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doPush("At the mean time you may connect to #SFO_FREE_WIFI for uninterrupted connectivity.");
    }

    public static void siaIsBack() {
        doPush("Hi Jie Xun, Sia is back!");
    }


    public static void sendFirstMessage() {
        doPush("Hello, my name is Sia! I'm here to answer any queries you might have with regards to Singapore Airlines " +
                "and guide you through your journey with our company. How may I help you?");
    }

    public static void askForCheckIn() {
        doPush("A reminder that your flight is in 2 days time. Would you like to do an online check in now?");
    }

    public static void sendNews() {
        doPush("Hi Jie Xun! Don’t forget that you have booked a flight to San Francisco on 5th December. " +
                "Remember to pack your bags! Also, do note that SIA does not allow users of the latest Samsung Galaxy Note 7 " +
                "to bring their phones on-board our planes. Thank you!");
    }
}

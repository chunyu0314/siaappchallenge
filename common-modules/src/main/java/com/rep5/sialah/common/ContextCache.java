package com.rep5.sialah.common;

import com.rep5.sialah.common.models.Context;
import com.rep5.sialah.common.models.SiaData;
import com.rep5.sialah.common.models.StewardReceipt;

/**
 * Created by low on 3/10/16 10:00 PM.
 */
public class ContextCache {
    private static Context context;
    private static boolean talkToCus;
    private static StewardReceipt steward = null;
    private static int id = 0;

    public static StewardReceipt getAndRemoveSteward() {
        StewardReceipt store = steward;
        steward = null;
        return store;
    }

    public static void setSteward(StewardReceipt newSteward) {
        steward = newSteward;
    }

    public static int getNewId() {
        return id++;
    }

    public static void talkToCus() {
        talkToCus = true;
    }

    public static void stopTalkToCus() {
        talkToCus = false;
    }

    public static boolean isTalkToCus() {
        return talkToCus;
    }

    public static Context getContext() {
        if (context == null) {
            init();
        }
        return context;
    }

    public static Context getContextAndReset() {
        if (context == null) {
            init();
        }
        context.getSiaData().setPlaneChat(false);
        context.getSiaData().setFakeBooking(false);
        context.getSiaData().setCustomerRequestType(null);
        return context;
    }

    private static void init() {
        SiaData siaData = new SiaData();
        siaData.setInFlight(false);
        siaData.setFakeBooking(false);
        siaData.setPlaneChat(false);
        siaData.setConfirmingCheckIn(false);
        siaData.setNeedsCustomerService(false);
        siaData.setIsCusService(false);

        context = new Context();
        context.setSiaData(siaData);
    }

    public static void cacheContext(Context context) {
        ContextCache.context = context;
    }

    public static void inFlight() {
        getContextAndReset().getSiaData().setInFlight(true);
    }

    public static void offFlight() {
        getContextAndReset().getSiaData().setInFlight(false);
    }
}

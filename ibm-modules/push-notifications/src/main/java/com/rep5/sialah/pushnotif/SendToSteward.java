package com.rep5.sialah.pushnotif;

import com.rep5.sialah.common.ContextCache;
import com.rep5.sialah.common.RestClient;
import com.rep5.sialah.common.models.Constants;
import com.rep5.sialah.common.models.StewardReceipt;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 * Created by low on 4/10/16 3:05 PM.
 */
public class SendToSteward {

    private static final String URL = "http://sia-steward-board-sia-steward-board.44fs.preview.openshiftapps.com/api/users";

    public static void fakeSend() {
        StewardReceipt msg = new StewardReceipt();
        msg.setId(ContextCache.getNewId());
        msg.setRequestItem("Show me some red wine");
        msg.setRequestType(Constants.STEWARD_REQUEST_SHOP);
        msg.setSeatNumber("14E");

        send(msg);
    }

    public static void send(StewardReceipt msg) {
        RestClient.getTarget(URL).request().post(Entity.entity(msg, MediaType.APPLICATION_JSON));
    }
}

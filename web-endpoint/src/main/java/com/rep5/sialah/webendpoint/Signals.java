package com.rep5.sialah.webendpoint;

import com.rep5.sialah.common.ContextCache;
import com.rep5.sialah.common.models.ChatBotMessage;
import com.rep5.sialah.pushnotif.SendToSteward;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by low on 4/10/16 2:52 PM.
 */

@Path("signals")
public class Signals {

    @GET
    @Path("start")
    public  Response sendInit() {
        Handler.sendFirstMessage();
        return Response.accepted().build();
    }

    @GET
    @Path("baggage")
    public Response getQueue() {
        Handler.sendBaggage();
        return Response.accepted().build();
    }

    @GET
    @Path("on_flight")
    public Response onFlight() {
        ContextCache.inFlight();
        Handler.boardedPlane();
        return Response.accepted().build();
    }

    @GET
    @Path("flight_schedule")
    public Response sendSchedule() {
        Handler.sendSchedule();
        return Response.accepted().build();
    }

    @GET
    @Path("check_in")
    public Response askForCheckIn() {
        Handler.askForCheckIn();
        ContextCache.getContext().getSiaData().setConfirmingCheckIn(true);
        return Response.accepted().build();
    }

    @GET
    @Path("one_day_before")
    public Response oneDayBefore() {
        Handler.sendCheckin();
        return Response.accepted().build();
    }

    @GET
    @Path("off_flight")
    public Response offFlight() {
        ContextCache.offFlight();
        Handler.reachedDestination();
        return Response.accepted().build();
    }

    @GET
    @Path("send_fake_steward")
    public Response fakeSend() {
        SendToSteward.fakeSend();
        return Response.accepted().build();
    }

    @POST
    @Path("cus_service")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response talkToCus(ChatBotMessage[] convo) {
        if (ContextCache.isTalkToCus()) {
            ContextCache.stopTalkToCus();
            return Response.ok().build();
        }
        Handler.handleService(convo);
        ContextCache.talkToCus();
        return Response.accepted().build();
    }

    @GET
    @Path("cus_service")
    public Response stopTalkToCus() {
        ContextCache.stopTalkToCus();
        return Response.accepted().header("Access-Control-Allow-Origin", "*").build();
    }
}

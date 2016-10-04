package com.rep5.sialah.webendpoint;

import com.rep5.sialah.common.ContextCache;
import com.rep5.sialah.common.models.StoreConvo;
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
    @Path("on_flight")
    public Response onFlight() {
        ContextCache.inFlight();
        return Response.accepted().build();
    }

    @GET
    @Path("off_flight")
    public Response offFlight() {
        ContextCache.offFlight();
        return Response.accepted().build();
    }

    @GET
    @Path("send_steward")
    public Response fakeSend() {
        SendToSteward.fakeSend();
        return Response.accepted().build();
    }

    @POST
    @Path("cus_service")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response talkToCus(StoreConvo convo) {
        Handler.handleService(convo);
        ContextCache.talkToCus();
        return Response.accepted().build();
    }

    @GET
    @Path("cus_service")
    public Response stopTalkToCus() {
        ContextCache.stopTalkToCus();
        return Response.accepted().build();
    }
}

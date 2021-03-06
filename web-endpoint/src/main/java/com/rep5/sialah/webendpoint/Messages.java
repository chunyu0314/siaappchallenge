package com.rep5.sialah.webendpoint;

import com.rep5.sialah.common.ContextCache;
import com.rep5.sialah.common.ConvoStore;
import com.rep5.sialah.common.CustomerData;
import com.rep5.sialah.common.MessageStore;
import com.rep5.sialah.common.models.PlaneChatPacket;
import com.rep5.sialah.common.models.SiaMessage;
import com.rep5.sialah.common.models.StewardReceipt;
import com.rep5.sialah.externalapi.BoardingPass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by low on 27/7/16 3:47 PM.
 */

@Path("messages")
public class Messages {

    private static final Logger logger = LoggerFactory.getLogger(Messages.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("plane_chat")
    public Response planeChat(PlaneChatPacket packet) {
        PlaneChat.handlePacket(packet);
        return Response.accepted().build();
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("fcm_id")
    public Response postId(String token) {
        logger.info("Received session token: " + token);
        if (token.startsWith("JX-")) {
            CustomerData.setFirebaseToken(token.substring(3));
        }
        else {
            CustomerData.setFriendToken(token);
        }
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("boarding_pass")
    public Response getBoardingPass() {
        return Response.ok(BoardingPass.getBoardingPass(), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response send(String message) {
        Handler.handleText(message);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("service_init")
    public Response serviceInit() {
        if (ContextCache.isTalkToCus()) {
            return Response.ok(ConvoStore.getMessages(), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.noContent().build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("service")
    public Response cusReceipt() {
        String reply = MessageStore.getAndClear();
        if (reply != null) {
            return Response.ok(reply, MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.noContent().build();
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("service")
    public Response cusReply(String reply) {
        Handler.handleServiceReply(reply);
        return Response.accepted().header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("steward")
    public Response stewardPoll() {
        StewardReceipt receipt = ContextCache.getAndRemoveSteward();
        if (receipt == null) {
            return Response.noContent().build();
        }
        else {
            return Response.ok(receipt, MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("steward")
    public Response stewardReply(String status) {
        Handler.handleSteward(status);
        return Response.ok().header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("test")
    public Response test(String test) {
        return Response.ok(test, MediaType.TEXT_PLAIN).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("test")
    public Response testGet() {
        return Response.ok("This is WORKING", MediaType.TEXT_PLAIN).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("transaction")
    public Response sendMessage(SiaMessage msg) {
        MessageFactory.send("test", msg);
        return Response.ok(MessageFactory.receive(), MediaType.APPLICATION_JSON).build();
    }
    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{transactionId}")
    public Response retrieveResponse(@PathParam("transactionId") String transactionId) {
        ResultResponse result = ResponseRetrieve.retrieve(transactionId);
        if (result == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("{transactionId}")
    public Response acknowledge(@PathParam("transactionId") String transactionId) {
        ResponseRetrieve.acknowledge(transactionId);
        return Response.accepted().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{queryType}")
    public Response testQuery(@PathParam("queryType") String queryType, ClefQuery query) {
        MessageFactory.send(queryType, query);
        return Response.accepted().build();
    }
    */
}

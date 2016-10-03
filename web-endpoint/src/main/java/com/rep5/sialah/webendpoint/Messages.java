package com.rep5.sialah.webendpoint;

import com.rep5.sialah.common.CustomerData;
import com.rep5.sialah.common.models.SiaMessage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by low on 27/7/16 3:47 PM.
 */

@Path("messages")
public class Messages {

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("fcm_id")
    public Response postId(String token) {
        CustomerData.setFirebaseToken(token);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("boarding_pass")
    public Response getBoardingPass() {
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response send(SiaMessage message) {
        Handler.handleText(message);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cus_service")
    public Response cusService(SiaMessage message) {
        Handler.handleServiceReply(message);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/steward")
    public Response stewardReply(SiaMessage message) {
        Handler.handleSteward(message);
        return Response.ok().build();
    }

    @POST
    @Path("test")
    public Response test(String test) {
        return Response.ok(test, MediaType.TEXT_PLAIN).build();
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
        return Response.ok(MessageFactory.receiveTest(), MediaType.APPLICATION_JSON).build();
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

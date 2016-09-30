package com.rep5.sialah.webendpoint;

import com.clef.infra.commons.models.ClefQuery;
import com.clef.infra.commons.models.ResultResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by low on 27/7/16 3:47 PM.
 */

@Path("query")
public class Query {

    @Secured
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

    @Secured
    @DELETE
    @Path("{transactionId}")
    public Response acknowledge(@PathParam("transactionId") String transactionId) {
        ResponseRetrieve.acknowledge(transactionId);
        return Response.accepted().build();
    }

    @Secured
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{queryType}")
    public Response testQuery(@PathParam("queryType") String queryType, ClefQuery query) {
        MessageFactory.send(queryType, query);
        return Response.accepted().build();
    }
}

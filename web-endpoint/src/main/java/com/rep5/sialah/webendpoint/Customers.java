package com.rep5.sialah.webendpoint;

import com.alibaba.fastjson.JSON;
import com.clef.infra.commons.models.ClefMessage;
import com.clef.infra.commons.models.RestPacket;
import com.clef.infra.commons.models.Signup;
import com.clef.infra.kafka.ClefProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("customers")
public class Customers {

    private static Logger logger = LoggerFactory.getLogger(Customers.class);
/*
    @GET
    @Secured
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        logger.info("test request");
        logger.info(MessageSort.getInstanceId());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("finished sleeping for "+MessageSort.getInstanceId());
        return "thats it";
    }

    @POST
    @Path("signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String signup(Signup signup) {
        SendGmail.sendkey(signup.getEmail(), UUID.randomUUID().toString());
        return "Signup success";
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    /*
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput getCustomer() {
        final EventOutput output = new EventOutput();
        logger.info("request received");
        String id = UUID.randomUUID().toString();
        RestPacket packet = new RestPacket();
        packet.setId(id);
        packet.setMsg("getCustomers");

        Thread t = new Thread(() -> {
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                logger.info("interrupted");
            }
            logger.info("finished sleeping");
            //TODO continue to do something

        });
        t.start();
        MessageSort.addListeners(id, message -> {
            try{
                    final OutboundEvent.Builder eventBuilder
                            = new OutboundEvent.Builder();
                    eventBuilder.name("message-to-client");
                    eventBuilder.data(String.class, message);
                    final OutboundEvent event = eventBuilder.build();
                    output.write(event);
            } catch (IOException e) {
                throw new RuntimeException(
                        "Error when writing the event.", e);
            } finally {
                try {
                    output.close();
                } catch (IOException ioClose) {
                    throw new RuntimeException(
                            "Error when closing the event output.", ioClose);
                }
            }
            t.interrupt();
        });
        ClefProducer producer = new ClefProducer();
        producer.write("rest", "0", JSON.toJSONString(packet));
        logger.info("written " + JSON.toJSONString(packet));
        return output;

    }
*/
    @GET
    @Produces("application/json")
    @Path("{customer_id}")
    public List<ClefMessage> getCustomerTransactions(@PathParam("customer_id") String customerId) {
        List a = new ArrayList();
        a.add(new ClefMessage());
        return a;
    }
}

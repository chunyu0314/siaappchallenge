package com.rep5.sialah.convo;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Created by low on 3/10/16 10:41 PM.
 */
public class ConvoClient {
    private static Client watsonClient;

    public static WebTarget getWatsonTarget() {
        if (watsonClient == null) {
            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder()
                    .nonPreemptive()
                    .credentials(ConvoProperties.USERNAME, ConvoProperties.PASSWORD)
                    .build();
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.register(feature) ;
            watsonClient = ClientBuilder.newBuilder().withConfig(clientConfig).register(JacksonFeature.class).build();
            //watsonClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
        }
        return watsonClient.target(ConvoProperties.URL);
    }
}

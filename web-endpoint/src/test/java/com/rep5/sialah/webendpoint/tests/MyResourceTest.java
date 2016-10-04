package com.rep5.sialah.webendpoint.tests;

import com.rep5.sialah.webendpoint.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;


public class MyResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Ignore
    @Before
    public void setUp() throws Exception {
        server = Main.startServer();

        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @Ignore
    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Ignore
    @Test
    public void run() {
        try {
            Thread.sleep(1300000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        target.request().async().get();
    }
}

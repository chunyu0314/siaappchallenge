package com.rep5.sialah.externalapi;

import com.alibaba.fastjson.JSONObject;
import com.rep5.sialah.common.RestClient;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;

/**
 * Created by low on 6/10/16 9:07 AM.
 */
public class FlightInfo {
    private static final String url = "https://flifo-qa.api.aero/flifo/v3/flights/sin/d/?futureWindow=4";

    private static final String apiKey = "2cfd0827f82ceaccae7882938b4b1627";
    public static String getStatus(String city) {
        Response response = RestClient.getTarget(url).path(city).request().header("X-apiKey", apiKey).accept(MediaType.APPLICATION_JSON).get();
        JSONObject result = response.readEntity(JSONObject.class);
        return result.toJSONString();

    }
}

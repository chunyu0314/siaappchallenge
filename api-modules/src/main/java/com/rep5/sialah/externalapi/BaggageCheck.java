package com.rep5.sialah.externalapi;

import com.alibaba.fastjson.JSONObject;
import com.rep5.sialah.common.RestClient;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by low on 4/10/16 5:19 PM.
 */
public class BaggageCheck {
    private static final String url = "https://bagjourney.sita.aero/baggage/history/v1.0/tag/";
    private static final String apiKey = "7989ca6cbadb38855a6112a2eab0d594";

    public static boolean getBaggage(String id, String date) {
        Response response = RestClient.getTarget(url).path(id).path("flightdate").path(date).request().header("X-apiKey", apiKey).accept(MediaType.APPLICATION_JSON).get();
        JSONObject result = response.readEntity(JSONObject.class);
        String eventCode = result.getJSONArray("events").getJSONObject(0).getString("event_code");
        return eventCode.matches("LOADED_ON_AIRCRAFT");

    }

}

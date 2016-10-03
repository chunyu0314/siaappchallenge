package com.rep5.sialah.externalapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rep5.sialah.common.RestClient;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.util.Date;

/**
 * Created by low on 3/10/16 3:17 PM.
 */
public class WaitTime {
    private static final String url = "https://waittime-qa.api.aero/waittime/v1/projection";
    private static final String apiKey = "8e2cff00ff9c6b3f448294736de5908a";
    public static Duration getWaitTime(String city) {
        Response response = RestClient.getTarget(url).path(city).request().header("X-apiKey", apiKey).accept(MediaType.APPLICATION_JSON).get();
        JSONObject result = response.readEntity(JSONObject.class);
        long time = result.getJSONArray("projections").getJSONObject(0).getIntValue("projectedWaitTime");
        return Duration.ofSeconds(time);

    }

    public static Duration getWaitTime() {
        return getWaitTime("SIN");

    }
}

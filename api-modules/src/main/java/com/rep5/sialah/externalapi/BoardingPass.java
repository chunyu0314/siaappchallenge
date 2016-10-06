package com.rep5.sialah.externalapi;

import com.alibaba.fastjson.JSONObject;
import com.rep5.sialah.common.RestClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by low on 6/10/16 9:07 AM.
 */
public class BoardingPass {
    private static final String url = "http://apidev.singaporeair.com/csl/getBoardingPassQRCode?";
    private static final String apiKey = "a577f3992827c60ff1c953b2eae921db";
    private static final String input = "%7B%22pnrReferenceNo%22%3A%225SXQFZ%22%2C%22pax0%22%3A%7B%22uci%22%3A%222301C883000071DA%22%2C%22dids%22%3A%5B%222301D8830000DAAB%22%2C%222301C8830000D902%22%5D%2C%22pax1%22%3A%7B%22uci%22%3A%222301C883000071DB%22%2C%22dids%22%3A%5B%222301D8830000DAAC%22%2C%222301C8830000D903%22%5D%7D%7D%7D";
    private static final String data = "{\"pnrReferenceNo\":\"248LJV\",\"pax0\":{\"uci\":\"2301D78000006CA2\",\"dids\":[\"2301D78000006503\"]}}";
    public static JSONObject getBoardingPass() {
        Response response = RestClient.getTarget(url).queryParam("inputParams", input).request().header("user-key", apiKey)
                .accept(MediaType.APPLICATION_JSON).post(Entity.entity(data, MediaType.APPLICATION_JSON));
        JSONObject result = response.readEntity(JSONObject.class);
        return result;

    }
}

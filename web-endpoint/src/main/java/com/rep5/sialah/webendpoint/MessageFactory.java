package com.rep5.sialah.webendpoint;

import com.alibaba.fastjson.JSON;
import com.clef.infra.commons.models.ClefQuery;
import com.clef.infra.kafka.ClefProducer;

/**
 * Created by low on 16/8/16 2:58 PM.
 */
public class MessageFactory {
    static private ClefProducer producer;

    public static void init() {
        producer = new ClefProducer();
    }

    public static void send(String queryType, ClefQuery query) {
        //TODO check for illegal query type
        producer.write("clef-" + queryType, JSON.toJSONString(query));
    }
}

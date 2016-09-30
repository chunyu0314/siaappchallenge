package com.rep5.sialah.webendpoint;

import com.alibaba.fastjson.JSON;
import com.rep5.sialah.common.models.SiaMessage;
import com.rep5.sialah.ibm.kafka.SiaProducer;

/**
 * Created by low on 16/8/16 2:58 PM.
 */
public class MessageFactory {
    static private SiaProducer producer;

    public static void init() {
        producer = new SiaProducer();
    }

    public static void send(String topic, SiaMessage msg) {
        //TODO check for illegal query type
        producer.write(topic, JSON.toJSONString(msg));
    }
}

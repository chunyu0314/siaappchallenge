package com.rep5.sialah.webendpoint;

import com.alibaba.fastjson.JSON;
import com.rep5.sialah.common.models.SiaMessage;
import com.rep5.sialah.externalapi.BaggageCheck;
import com.rep5.sialah.ibm.kafka.SiaConsumer;
import com.rep5.sialah.ibm.kafka.SiaProducer;

import java.util.List;

/**
 * Created by low on 16/8/16 2:58 PM.
 */
public class MessageFactory {
    static private SiaProducer producer;
    static private SiaConsumer testConsumer;

    public static void init() {
        producer = new SiaProducer();
        testConsumer = new SiaConsumer("server", "test");
    }

    public static void send(String topic, SiaMessage msg) {
        //TODO check for illegal query type
        producer.write(topic, JSON.toJSONString(msg));
    }

    public static SiaMessage receive() {
        List<String> retrieved = testConsumer.manualPoll();
        if (retrieved.size() == 1) {
            return JSON.parseObject(retrieved.get(0), SiaMessage.class);
        }
        pollBaggage();
        return null;
    }

    public static void pollBaggage() {
        Thread t = new Thread(() -> {
            while (true) {
                BaggageCheck.getBaggage("id", "2015-10-14");
            }
        });
        t.start();
    }
}

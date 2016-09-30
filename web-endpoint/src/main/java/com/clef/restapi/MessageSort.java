package com.clef.restapi;/*
package com.clef.restapi;

import com.alibaba.fastjson.JSON;
import com.clef.infra.commons.models.RestPacket;
import com.clef.infra.commons.services.ClefConfigService;
import com.clef.infra.kafka.ClefConsumer;
import com.clef.infra.kafka.ConsumerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageSort {

    private static ClefConsumer consumer;
    private static Map<String, MessageListener> listeners = new HashMap<>();
    private static String instanceId;
    private static Logger logger = LoggerFactory.getLogger(MessageSort.class);

    public static synchronized void addListeners(String id, MessageListener listener) {
        listeners.put(id, listener);
    }

    public static synchronized void removeListeners(String id) {
        listeners.remove(id);
    }

    public static String getInstanceId() {
        return instanceId;
    }

    public static void init(String instanceId) {
        MessageSort.instanceId = instanceId;
        List<String> topics = new ArrayList<>();
        topics.add("rest");
        consumer = new ClefConsumer(1, "rest " + instanceId, topics);

        ConsumerListener listener = (data) -> {
            logger.info("message sorter received: " + data);
            RestPacket packet = new RestPacket();
            packet = JSON.parseObject(data, RestPacket.class);
            MessageListener msgListener = listeners.get(packet.getId());
            if (msgListener != null) {
                msgListener.onReceived(packet.getMsg());
                listeners.remove(packet.getId(), msgListener);
            }
        };

        consumer.addListener(listener);
        Thread thread = new Thread(consumer);
        thread.start();
        logger.info("Clef Consumer initiated");
    }

    public static void close() {
        consumer.shutdown();
    }
}
*/

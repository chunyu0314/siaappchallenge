package com.rep5.sialah.ibm.kafka.tests;

import com.rep5.sialah.common.models.ClefMessage;
import com.rep5.sialah.ibm.kafka.SiaConsumer;
import com.rep5.sialah.ibm.kafka.SiaProducer;
import com.rep5.sialah.ibm.kafka.ConsumerListener;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by low on 17/5/16.
 * Complete test of kafka producer and consumer, requires kafka service running
 */
public class KafkaTest {
    @Ignore
    @Test
    public void testKafka(){
        String testString = "TEST " + new Date(System.currentTimeMillis());
        String topic = "test";
        List<String> topics = new ArrayList<>();
        SiaConsumer consumer;
        SiaProducer producer = new SiaProducer();
        ClefMessage msg = new ClefMessage();
        //final String[] receiptString = new String[1];

        topics.add(topic);
        consumer = new SiaConsumer(1, "es-group", topics);

        ConsumerListener listener = (data) -> {
           msg.setInstance(data);
            //receiptString[0] = data;
        };

        consumer.addListener(listener);
        Thread thread = new Thread(consumer);
        thread.start();

        try {
            sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        producer.write(topic, testString);
        try {
            sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        consumer.shutdown();

        Assert.assertEquals(testString, msg.getInstance());
    }
}

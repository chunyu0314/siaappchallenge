package com.rep5.sialah.ibm.kafka;

import com.sun.jmx.remote.internal.ArrayQueue;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by low on 13/5/16.
 * Consumer instance
 */
public class SiaConsumer implements Runnable {
    private KafkaConsumer<String, String> consumer;
    private List<String> topics;
    private int id;
    private List<TopicPartition> partition = new ArrayList<>();
    private List<ConsumerListener> listeners = new ArrayList<>();

    private static Logger logger = LoggerFactory.getLogger(SiaConsumer.class);

    private boolean isPartitioned = false;

    public void addListener(ConsumerListener newListener){
        listeners.add(newListener);
    }

    public SiaConsumer(int id, String groupId, List<String> topics, int partitionNumber) {
        setup(id, groupId, topics);
        for (String topic: topics) {
            this.partition.add(new TopicPartition(topic, partitionNumber));
        }
        isPartitioned = true;
    }

    public SiaConsumer(int id, String groupId, List<String> topics) {
        setup(id, groupId, topics);
    }

    private void setup(int id, String groupId, List<String> topics) {
        this.id = id;
        this.topics = topics;
        Properties prop = KafkaProperties.getClientConfiguration(KafkaProperties.getKafkaHost(), KafkaProperties.getApiKey(), false);
        this.consumer = new KafkaConsumer<>(prop);
        if (isPartitioned) {
            consumer.assign(partition);
        }
        else {
            consumer.subscribe(topics);
        }
        logger.info("Consumer subscribed");

    }

    public List<String> manualPoll() {

        List<String> list = new ArrayList<>();
        ConsumerRecords<String, String> records = consumer.poll(300L);
        for (ConsumerRecord<String, String> record : records) {
            list.add(record.value());
        }
        return list;
    }


    @Override
    public void run() {
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(300L);
                for (ConsumerRecord<String, String> record : records) {

                    for (ConsumerListener cl: listeners) {
                        cl.onReceipt(record.value());
                    }
                    logger.info("\"" + record.value() + "\" received from consumer");
                }
            }
        }
        catch (WakeupException e) {
            logger.info("wake up exception");
            // ignore for shutdown
        }
    }

    public void shutdown() {
        consumer.wakeup();
        consumer.close();
    }
}


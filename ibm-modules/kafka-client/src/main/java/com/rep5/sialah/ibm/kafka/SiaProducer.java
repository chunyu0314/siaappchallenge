package com.rep5.sialah.ibm.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by low on 14/5/16.
 * Producer instance
 */
public class SiaProducer implements AutoCloseable {

    private Producer<String, String> producer;


    private static Logger logger = LoggerFactory.getLogger(SiaProducer.class);

    public SiaProducer() {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", KafkaProperties.KAFKA_BOOTSTRAP);
        prop.put("request.required.acks", KafkaProperties.REQUEST_ACK);
        prop.put("key.serializer", KafkaProperties.KEY_SERIALIZER);
        prop.put("value.serializer", KafkaProperties.VALUE_SERIALIZER);
        producer = new KafkaProducer<>(prop);
    }

    public void write(String topic, String json) {
        logger.info("sending from producer...");
        producer.send(new ProducerRecord<>(topic, json), (metadata, e) -> {
            if(e != null) {
                logger.warn("exception while sending " + json);
                e.printStackTrace();
            }
            logger.info("Offset of " + json + ": " + metadata.offset());
        });

        /*producer.flush();
        if (future.isCancelled()) {
            logger.info("Send failed");
        }
        else if (future.isDone()) {*/
            logger.info("\"" + json + "\"" + " sent");
        //}
    }

    @Override
    public void close() throws Exception {
        if(producer != null){
            producer.close();
            producer = null;
        }
    }

    public boolean isOpened(){
        return producer != null;
    }
}

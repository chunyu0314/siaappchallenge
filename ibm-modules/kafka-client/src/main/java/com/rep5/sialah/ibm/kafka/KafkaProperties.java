package com.rep5.sialah.ibm.kafka;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * Created by low on 23/5/16.
 * Properties used by kafka
 */
class KafkaProperties {
//    static final String KAFKA_BOOTSTRAP = "13.92.250.75:9092";
    static final String KAFKA_BOOTSTRAP = "23.96.61.60:9092";
    static final String PARTITIONER_CLASS = "com.clef.infra.kafka.KafkaPartitioner";
    static final String KEY_SERIALIZER = StringSerializer.class.getName();
    static final String VALUE_SERIALIZER = StringSerializer.class.getName();
    static final String KEY_DESERIALIZER = StringDeserializer.class.getName();
    static final String VALUE_DESERIALIZER = StringDeserializer.class.getName();
    static final String REQUEST_ACK = "1";
}

package com.rep5.sialah.ibm.kafka;

import com.rep5.sialah.common.models.MessageHubCredentials;
import com.rep5.sialah.common.models.MessageHubEnvironment;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Properties;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by low on 23/5/16.
 * Properties used by kafka
 */
class KafkaProperties {
    private static final String JAAS_CONFIG_PROPERTY = "java.security.auth.login.config";
    private static final long HOUR_IN_MILLISECONDS = 3600000L;
    private static final Logger logger = Logger.getLogger(KafkaProperties.class);
    private static String userDir, resourceDir;
    private static boolean isDistribution;
    private static String kafkaHost = null;
    private static String restHost = null;
    private static String apiKey = null;

    public static String getKafkaHost() {
        if (kafkaHost == null) {
            init();
        }
        return kafkaHost;
    }
    public static String getApiKey() {
        if (apiKey == null) {
            init();
        }
        return apiKey;
    }

    private static void init() {
        userDir = System.getProperty("user.dir");

        isDistribution = new File(userDir + File.separator + ".java-buildpack").exists();

        if(isDistribution) {
            logger.log(Level.INFO, "Running in distribution mode.");
            resourceDir = userDir + File.separator + "message-hub-kafka-ssl-1.0" + File.separator + "bin" + File.separator + "resources";
        } else {
            logger.log(Level.INFO, "Running in local mode.");
            resourceDir = userDir + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        }

        // Set JAAS configuration property.
        if(System.getProperty(JAAS_CONFIG_PROPERTY) == null) {
            System.setProperty(JAAS_CONFIG_PROPERTY, resourceDir + File.separator + "jaas.conf");
        }

        logger.log(Level.INFO, "Starting Message Hub Java Sample");
        // Arguments parsed via VCAP_SERVICES environment variable.
        //String vcapServices = System.getenv("VCAP_SERVICES");
        String vcapServices = "{\"conversation\":[{\"credentials\":{\"url\":\"https://gateway.watsonplatform.net/conversation/api\"," +
                "\"password\":\"As68HA3nuw5i\",\"username\":\"d8d15f18-5573-420e-b564-e104fe4e8fb7\"},\"syslog_drain_url\":null," +
                "\"label\":\"conversation\",\"provider\":null,\"plan\":\"standard\",\"name\":\"Conversation-ru\"," +
                "\"tags\":[\"watson\",\"ibm_created\",\"ibm_dedicated_public\"]}],\"messagehub\":[{\"credentials\":" +
                "{\"mqlight_lookup_url\":\"https://mqlight-lookup-prod01.messagehub.services.us-south.bluemix.net/Lookup?serviceId=38932679-a9b0-4e54-93f3-76f25d307552\"," +
                "\"api_key\":\"HpJPQ62iWa8mTcH4vcAF1ayuZdN6sMXbCzxXHJhQdhG11pgf\",\"kafka_admin_url\":\"https://kafka-admin-prod01.messagehub.services.us-south.bluemix.net:443\"," +
                "\"kafka_rest_url\":\"https://kafka-rest-prod01.messagehub.services.us-south.bluemix.net:443\",\"kafka_brokers_sasl\":" +
                "[\"kafka01-prod01.messagehub.services.us-south.bluemix.net:9093\",\"kafka02-prod01.messagehub.services.us-south.bluemix.net:9093\"," +
                "\"kafka03-prod01.messagehub.services.us-south.bluemix.net:9093\",\"kafka04-prod01.messagehub.services.us-south.bluemix.net:9093\"," +
                "\"kafka05-prod01.messagehub.services.us-south.bluemix.net:9093\"],\"user\":\"HpJPQ62iWa8mTcH4\",\"password\":\"vcAF1ayuZdN6sMXbCzxXHJhQdhG11pgf\"}," +
                "\"syslog_drain_url\":null,\"label\":\"messagehub\",\"provider\":null,\"plan\":\"standard\",\"name\":\"MessageHub-24\"," +
                "\"tags\":[\"ibm_dedicated_public\",\"web_and_app\",\"ibm_created\"]}],\"imfpush\":[{\"credentials\":{\"appGuid\":\"d7923041-d143-4dea-a75a-b40063b4008e\"," +
                "\"url\":\"http://imfpush.ng.bluemix.net/imfpush/v1/apps/d7923041-d143-4dea-a75a-b40063b4008e\"," +
                "\"admin_url\":\"//mobile.ng.bluemix.net/imfpushdashboard/?appGuid=d7923041-d143-4dea-a75a-b40063b4008e\"," +
                "\"appSecret\":\"ab4348d2-688b-4e72-a160-672d61752ec4\",\"clientSecret\":\"639e8480-25e2-439f-a759-ff4251bfd644\"}," +
                "\"syslog_drain_url\":null,\"label\":\"imfpush\",\"provider\":null,\"plan\":\"Basic\",\"name\":\"PushNotifications-q3\"," +
                "\"tags\":[\"mobile\",\"ibm_created\",\"ibm_dedicated_public\"]}]}";
        ObjectMapper mapper = new ObjectMapper();

        if(vcapServices != null) {
            try {
                // Parse VCAP_SERVICES into Jackson JsonNode, then map the 'messagehub' entry
                // to an instance of MessageHubEnvironment.
                JsonNode vcapServicesJson = mapper.readValue(vcapServices, JsonNode.class);
                ObjectMapper envMapper = new ObjectMapper();
                String vcapKey = null;
                Iterator<String> it = vcapServicesJson.fieldNames();

                // Find the Message Hub service bound to this application.
                while (it.hasNext() && vcapKey == null) {
                    String potentialKey = it.next();

                    if (potentialKey.startsWith("messagehub")) {
                        logger.log(Level.INFO, "Using the '" + potentialKey + "' key from VCAP_SERVICES.");
                        vcapKey = potentialKey;
                    }
                }

                if (vcapKey == null) {
                    logger.log(Level.ERROR,
                            "Error while parsing VCAP_SERVICES: A Message Hub service instance is not bound to this application.");
                    return;
                }

                MessageHubEnvironment messageHubEnvironment = envMapper.readValue(vcapServicesJson.get(vcapKey).get(0).toString(), MessageHubEnvironment.class);
                MessageHubCredentials credentials = messageHubEnvironment.getCredentials();

                kafkaHost = credentials.getKafkaBrokersSasl()[0];
                restHost = credentials.getKafkaRestUrl();
                apiKey = credentials.getApiKey();

                updateJaasConfiguration(credentials);
            } catch(final Exception e) {
                e.printStackTrace();
                return;
            }
        } else {
            logger.log(Level.ERROR, "VCAP_SERVICES environment variable is null, are you running outside of Bluemix? If you are, consider the following usage:\n\n" +
                    "java -jar <name_of_jar>.jar <kafka_endpoint> <rest_endpoint> <api_key>");
            return;
        }

        logger.log(Level.INFO, "Sample will run until interrupted.");
        logger.log(Level.INFO, "Resource directory: " + resourceDir);
        logger.log(Level.INFO, "Kafka Endpoint: " + kafkaHost);
        logger.log(Level.INFO, "Rest API Endpoint: " + restHost);


    }

    private static void updateJaasConfiguration(MessageHubCredentials credentials) {
        updateJaasConfiguration(credentials.getUser(), credentials.getPassword());
    }

    private static void updateJaasConfiguration(String username, String password) {
        String templatePath = resourceDir + File.separator + "templates" + File.separator + "jaas.conf.template";
        String path = resourceDir + File.separator + "jaas.conf";
        OutputStream jaasStream = null;

        logger.log(Level.INFO, "Updating JAAS configuration");

        try {
            String templateContents = new String(Files.readAllBytes(Paths.get(templatePath)));
            jaasStream = new FileOutputStream(path, false);

            // Replace username and password in template and write
            // to jaas.conf in resources directory.
            String fileContents = templateContents
                    .replace("$USERNAME", username)
                    .replace("$PASSWORD", password);

            jaasStream.write(fileContents.getBytes(Charset.forName("UTF-8")));
        } catch (final FileNotFoundException e) {
            logger.log(Level.ERROR, "Could not load JAAS config file at: " + path);
        } catch (final IOException e) {
            logger.log(Level.ERROR, "Writing to JAAS config file:");
            e.printStackTrace();
        } finally {
            if(jaasStream != null) {
                try {
                    jaasStream.close();
                } catch(final Exception e) {
                    logger.log(Level.ERROR, "Closing JAAS config file:");
                    e.printStackTrace();
                }
            }
        }
    }

    public static final Properties getClientConfiguration(String broker, String apiKey, boolean isProducer) {
        Properties props = new Properties();
        InputStream propsStream;
        String fileName;

        if (isProducer) {
            fileName = "producer.properties";
        } else {
            fileName = "consumer.properties";
        }

        try {
            propsStream = new FileInputStream(resourceDir + File.separator + fileName);
            props.load(propsStream);
            propsStream.close();
        } catch (IOException e) {
            logger.log(Level.ERROR, "Could not load properties from file");
            return props;
        }

        props.put("client.id", apiKey);
        props.put("bootstrap.servers", broker);
        props.put("ssl.truststore.location", "/usr/lib/jvm/oracle_jdk8/jre/lib/security/cacerts");
        //TODO cacert location of liberty -> /home/vcap/app/.java/jre/lib/security/cacerts
        props.put("ssl.truststore.password", "changeit");

        if(isDistribution) {
            props.put("ssl.truststore.location", userDir + "/.java-buildpack/open_jdk_jre/lib/security/cacerts");
        }

        return props;
    }
   /*
    static final String KAFKA_BOOTSTRAP = "23.96.61.60:9092";
    static final String PARTITIONER_CLASS = "com.clef.infra.kafka.KafkaPartitioner";
    */
    static final String KEY_SERIALIZER = StringSerializer.class.getName();
    static final String VALUE_SERIALIZER = StringSerializer.class.getName();
    static final String KEY_DESERIALIZER = StringDeserializer.class.getName();
    static final String VALUE_DESERIALIZER = StringDeserializer.class.getName();
    static final String REQUEST_ACK = "1";
}

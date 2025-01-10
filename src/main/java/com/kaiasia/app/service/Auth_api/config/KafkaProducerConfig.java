//package com.kaiasia.app.service.Auth_api.config;
//
//import org.apache.kafka.clients.CommonClientConfigs;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.Producer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Properties;
//
//@Configuration
//public class KafkaProducerConfig {
//    @Autowired
//    private KafkaLoadConfig kafkaConfig;
//
//    @Bean
//    public Producer<String, String> producers() {
//        Properties configProps = new Properties();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootsTrapServer());
//        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, "TEST_API");
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
////        configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, Integer.MAX_VALUE);
////        if (kafkaConfig.isAuthentication()) {
////            String username = kafkaConfig.getUsernameACL();
////            String pw = kafkaConfig.getPwACL();
////            configProps.put("acks", "all");
////            configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
////            configProps.put("sasl.mechanism", "PLAIN");
////            configProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + username + "\" password=\"" + pw + "\";");
////        }
//        configProps.put("acks", "all");
//        return new KafkaProducer<>(configProps);
//    }
//}

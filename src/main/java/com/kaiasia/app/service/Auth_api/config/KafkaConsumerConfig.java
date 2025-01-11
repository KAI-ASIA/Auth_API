//package com.kaiasia.app.service.Auth_api.config;
//
//import org.apache.kafka.clients.CommonClientConfigs;
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaConsumerConfig {
//    @Autowired
//    private KafkaLoadConfig kafkaConfig;
//
//    @Bean
//    public Consumer<String, String> consumer(){
//        Map<String, Object> consumerProps1 = new HashMap<>();
//        consumerProps1.putAll(consumerProps());
//        return new KafkaConsumer<String, String>(consumerProps1);
//    }
//
//    public Map<String, Object> consumerProps() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootsTrapServer());
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "API_TEST");
////        props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, kafkaConfig.getClientId().concat("-").concat(AppUtil.getHostName()));
//        props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, kafkaConfig.getClientId());
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaConfig.getPollSize());
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
//        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "300000"); // 5
//
//        if (kafkaConfig.isAuthentication()) {
//            String username = kafkaConfig.getUsernameACL();
//            String pw = kafkaConfig.getPwACL();
//            props.put("acks", "all");
//            props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
//            props.put("sasl.mechanism", "PLAIN");
//            props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + username + "\" password=\"" + pw + "\";");
//        }
//        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1000000);
//        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 1000);
//        return props;
//    }
//}

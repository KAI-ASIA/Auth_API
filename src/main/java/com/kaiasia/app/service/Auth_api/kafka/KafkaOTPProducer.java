//package com.kaiasia.app.service.Auth_api.kafka;
//
//import com.kaiasia.app.service.Auth_api.model.DataSendToKafka;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.text.MessageFormat;
//
//@Component
//public class KafkaOTPProducer {
//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//    @Value("${kafka_getOTP.topic.name}")
//    private String topic;
//
//    @Value("${kafka_getOTP.email.subject}")
//    private String subject;
//
//    @Value("${kafka_getOTP.email.content}")
//    private String content;
//
//
//    public void sendMessage(String email, String getOTP)throws Exception{
//        DataSendToKafka data = new DataSendToKafka();
//        String formattedContent = MessageFormat.format(content,getOTP);
//        data.setEmail(email);
//        data.setSubject(subject);
//        data.setContent(formattedContent);
//
//        kafkaTemplate.send(topic, data);
//    }
//}

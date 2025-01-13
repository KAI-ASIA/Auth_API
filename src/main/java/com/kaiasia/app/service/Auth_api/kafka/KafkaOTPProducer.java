package com.kaiasia.app.service.Auth_api.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

//@Service
//public class KafkaOTPProducer {
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    public void sendMessage(String topic, String message){
//        kafkaTemplate.send(topic, message);
//    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void doTest(){
//        sendMessage("test1", "Xin chao kaiasia");
//    }
//}

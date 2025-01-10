package com.kaiasia.app.service.Auth_api.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiasia.app.service.Auth_api.model.DataSendToKafka;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkProducerSend {
    @Autowired
    private Producer<String, String> producers;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(String topic, String key, DataSendToKafka message) throws Exception{
        ProducerRecord<String, String> record = null;
        try{
            String jsonMessage = objectMapper.writeValueAsString(message);
            record = new ProducerRecord<>(topic, key, jsonMessage);
            producers.send(record);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

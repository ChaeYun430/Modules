package com.fhk.module;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final NewTopic newTopic;

    public OrderProducer(KafkaTemplate<String, Object> kafkaTemplate, NewTopic newTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.newTopic = newTopic;
    }

    public void sendOrders() {
        List<String> orderIds = List.of("101", "102", "103", "104", "105", "106");


        for (String orderId : orderIds) {
            String message = "{ \"orderId\": \"" + orderId + "\", \"item\": \"item-" + orderId + "\" }";

            kafkaTemplate.send(newTopic.name(), message);
        }
        kafkaTemplate.flush();
    }

}

package com.fhk.module.event;

import lombok.RequiredArgsConstructor;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final List<NewTopic> newTopics;

    public void sendOrders() {
        List<String> orderIds = List.of("101", "102", "103", "104", "105", "106");


        for (String orderId : orderIds) {
            String message = "{ \"orderId\": \"" + orderId + "\", \"item\": \"item-" + orderId + "\" }";

            kafkaTemplate.send(newTopics.get(0).name(), message);
        }
        kafkaTemplate.flush();
    }

}

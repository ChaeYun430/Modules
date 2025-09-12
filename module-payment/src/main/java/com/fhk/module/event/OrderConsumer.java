package com.fhk.module.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @KafkaListener(topics = "order-events", groupId = "payment-group")
    public void consumeOrder(){

    }

}

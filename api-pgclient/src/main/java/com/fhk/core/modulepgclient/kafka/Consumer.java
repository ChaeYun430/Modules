package com.fhk.core.modulepgclient.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

        @KafkaListener(topics = "test-topic1", groupId = "fhk-kafka")
        public void consume(String message) {
            System.out.println("Received message: " + message);
        }

}
package com.fhk.core.modulepgclient.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class KafkaTestController {

    private final Producer producer;

    @PostMapping
    public ResponseEntity<String> sendTest() {
        producer.sendMessage("test-topic1", "message1");
        return ResponseEntity.ok("Consumed?");
    }

}

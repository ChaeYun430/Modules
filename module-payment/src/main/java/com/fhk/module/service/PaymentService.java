package com.fhk.module.service;

import com.fhk.module.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;


    @KafkaListener(topics = "order-topic", groupId = "payment-group")
    public void consumeOrder(){

    }

}

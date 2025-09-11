package com.fhk.module.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // 특정 Partition(주문 ID 기반)만 처리
/*    @Override
    @KafkaListener(topicPartitions = @TopicPartition(topic = "inventory-events", partitions = {"1"}))
    public OrderRes createOrder(OrderReq req) {

        System.out.println("[OrderService] Partition 1 메시지 처리: " + orderId);

        // 재고 이벤트 발행
        kafkaTemplate.send("inventory-events", orderId);
    }*/

}

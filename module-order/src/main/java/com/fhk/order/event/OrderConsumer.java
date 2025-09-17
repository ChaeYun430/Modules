package com.fhk.order.event;

import com.fhk.outbox.dto.OrderDTO;
import com.fhk.worker.OrderWorker;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final RedisTemplate<String, Object> redisTemplate;
    private final OrderWorker orderWorker;

    @KafkaListener(topics = "order-topic", groupId = "payment-group", containerFactory = "paymentFactory")
    public void paymentConsumeOrder(ConsumerRecord<String, OrderDTO> record) {
        consumeOrder(record);
    }

    @KafkaListener(topics = "order-topic", groupId = "inventory-group", containerFactory = "inventoryFactory")
    public void inventoryConsumeOrder(ConsumerRecord<String, OrderDTO> record) {
        consumeOrder(record);
    }

    public void consumeOrder(ConsumerRecord <String, OrderDTO> record){
        String orderId = record.key();
        OrderDTO event = record.value();
        int workerIndex = Integer.getInteger(orderId) % 4;
        String queueKey = "orderWorkQueue : " + workerIndex;
        redisTemplate.opsForList().rightPush(queueKey, event);
        orderWorker.processPaymentQueue(queueKey,orderId, event);
    }

}

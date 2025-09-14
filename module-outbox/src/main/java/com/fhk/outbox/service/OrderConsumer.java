package com.fhk.outbox.service;

import com.fhk.outbox.constant.OrderStatus;
import com.fhk.outbox.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final RedisTemplate<String, Object> redisTemplate;

    @KafkaListener(topics = "order-topic", groupId = "payment-group")
    public void paymentConsumeOrder(ConsumerRecord<String, OrderDTO> record) {
        consumeOrder(record);
    }

    public void consumeOrder(ConsumerRecord <String, OrderDTO> record){
        String orderId = record.key();
        OrderDTO event = record.value();

        String queueKey = "order:" + orderId + ":queue";
        String pendingKey = "order:" + orderId + ":pending";
        String statusKey = "order:" + orderId + ":status";

        // 1. 큐에 이벤트 추가
        redisTemplate.opsForList().rightPush(queueKey, event.getStatus());

        // 2. 큐 처리
        processQueue(queueKey, statusKey, pendingKey);
    }


    private boolean processQueue(String queueKey, String statusKey, String pendingKey) {
        while (true) {

            String queueEvent = (String) redisTemplate.opsForList().index(queueKey, 0);
            String currentEvent = (String) redisTemplate.opsForHash().get(statusKey, "step");


            if (canProcess(currentEvent, queueEvent)) {

                // 상태 업데이트
                redisTemplate.opsForHash().put(statusKey, "step",currentEvent);
                // 큐에서 제거
                redisTemplate.opsForList().leftPop(queueKey);

                return true;
            } else {



                // 이전 단계 완료되지 않음 → 대기
                break;
            }
        }
        return false;
    }

    private boolean canProcess(String currentStatus, String eventType) {
        if (currentStatus == null && eventType.equals("order-created")) return true;
        if ("CREATED".equals(currentStatus) && eventType.equals("payment-approved")) return true;
        if ("PAID".equals(currentStatus) && eventType.equals("shipping-started")) return true;
        if ("SHIPPED".equals(currentStatus) && eventType.equals("notification")) return true;
        return false;
    }
}

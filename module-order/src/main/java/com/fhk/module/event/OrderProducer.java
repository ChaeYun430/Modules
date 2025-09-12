package com.fhk.module.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhk.module.dto.OrderReq;
import com.fhk.module.dto.OrderRes;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    @Value("${batch.size}")
    private int batchSize;

    @Value("${batch.interval-ms}")
    private long intervalMs;

    private final RedisTemplate<String, String> redisTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final List<NewTopic> newTopics;

    //배치 추출 + Kafka 전송
    @Scheduled(fixed = 1000) // 1초마다 실행
    public void processOrders() throws JsonProcessingException {
        List<OrderEvent> batch = new ArrayList<>();

        for (int i = 0; i < 100; i++) {

            String orderJson = redisTemplate.opsForList().leftPop("order:queue");

            if (orderJson == null) break;

            OrderRequest req = objectMapper.readValue(orderJson, OrderRequest.class);
            batch.add(new OrderEvent(req));
        }

        if (!batch.isEmpty()) {
            // DB Bulk Insert
            orderRepository.saveAll(batch.stream()
                    .map(OrderEntity::from)
                    .toList());

            // Kafka 발행
            for (OrderEvent event : batch) {
                kafkaTemplate.send("order-events", event.getOrderId(), objectMapper.writeValueAsString(event));
            }
        }
    }




    public void sendOrders(String orderId) {

        String orderJson = redisTemplate.opsForList().leftPop("orderQueue");

        String orderTopic = newTopics.get(0).name();

        List<String> orderIds = List.of("101", "102", "103", "104", "105", "106");


        for (String orderId : orderIds) {
            String message = "{ \"orderId\": \"" + orderId + "\", \"item\": \"item-" + orderId + "\" }";

            kafkaTemplate.send(orderTopic, message);
        }
        kafkaTemplate.flush();

    }

}

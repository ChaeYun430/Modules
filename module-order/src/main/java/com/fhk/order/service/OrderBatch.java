package com.fhk.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhk.core.dto.OrderDTO;
import com.fhk.core.dto.EventDTO;
import com.fhk.core.service.EventPublisher;
import com.fhk.outbox.service.EventPublisher;
import com.fhk.core.constant.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
@Import(EventPublisher.class)
public class OrderBatch {    //배치 추출 + Kafka 전송

    private final StringRedisTemplate stringRedisTemplate;
    private final EventPublisher eventPublisher;
    private final ObjectMapper objectMapper;
    private final List<NewTopic> newTopics;

    @Value("${batch.name}")
    private String redisKey;

    private final int trafficPoint = 1;


/*
    //1초마다 5개씩 보낸다.
    @Scheduled(fixedRate = 1000)
    public void batchBySize() throws JsonProcessingException {
        List<String> batch = new ArrayList<>();
        Long queueSize = stringRedisTemplate.opsForList().size(redisKey);
        if (queueSize == null || queueSize == 0) {
            return;
        }else if (queueSize < trafficPoint) {
            return;
        }else {
            for (int i = 0; i < queueSize; i++) {
                String messageJason = stringRedisTemplate.opsForList().leftPop(redisKey);
                batch.add(messageJason);
            }
        }
        produceOrder(batch);
        // 처리 후 Redis에서 제거
        stringRedisTemplate.opsForList().trim(redisKey, queueSize, queueSize-1);
    }
*/


    //5분마다 얼마나?? 다 지울 수는 없잖음...
    @Scheduled(cron = "0/5 * * * * *")
    public void batchByPeriod() throws JsonProcessingException {
        log.info("enter OrderBatch");
        List<String> batch = new ArrayList<>();
        Long queueSize = stringRedisTemplate.opsForList().size(redisKey);
        if (queueSize == null || queueSize == 0) {
            log.info("enter OrderBatch - queue size is 0");
            return;
        }
        for (int i = 0; i < queueSize; i++) {
            log.info("enter OrderBatch for(){}");
            String messageJason = stringRedisTemplate.opsForList().leftPop(redisKey);
            batch.add(messageJason);
        }
        log.info("enter OrderBatch to publish");
        produceOrder(batch);
        // 처리 후 Redis 비우기
        log.info("enter OrderBatch - to delete");
        stringRedisTemplate.delete(redisKey);
        log.info("enter OrderBatch - deleted");
    }


    public void produceOrder(List<String> batch) throws JsonProcessingException {
        String orderTopic = newTopics.get(0).name();
        for (String messageJason : batch) {
            OrderDTO message = objectMapper.readValue(messageJason, OrderDTO.class);

            EventDTO outboxDTO = EventDTO.builder()
                    .aggregateType(OrderStatus.PAID)
                    .aggregateId(message.getOrderId())
                    .topicName(orderTopic)
                    .payload(message)
                    .build();

            eventPublisher.publishEvent(outboxDTO);
        }
    }

}

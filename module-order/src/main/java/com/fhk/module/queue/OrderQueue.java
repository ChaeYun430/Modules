package com.fhk.module.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhk.module.RedisConfig;
import com.fhk.module.dto.OrderReq;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Import(RedisConfig.class)
public class OrderQueue {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisTemplate<String, String> redisTemplate;

    public void batchOrder(OrderReq orderReq) throws JsonProcessingException {

        String orderJson = objectMapper.writeValueAsString(orderReq);

        // fifo
        redisTemplate.opsForList().rightPush("orderQueue", orderJson);


    }
}

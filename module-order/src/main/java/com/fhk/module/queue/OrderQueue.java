package com.fhk.module.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhk.module.dto.OrderReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OrderQueue {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate;

    public void batchOrder(OrderReq orderReq) throws JsonProcessingException {

        String orderJson = objectMapper.writeValueAsString(orderReq);
        redisTemplate.opsForList().rightPush("order:queue", orderJson);
    }
}

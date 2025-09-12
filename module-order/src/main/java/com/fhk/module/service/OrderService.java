package com.fhk.module.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhk.module.domain.OrderEntity;
import com.fhk.module.domain.OrderRepository;
import com.fhk.module.dto.OrderReq;
import com.fhk.module.dto.OrderRes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService{

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();


    //주문 저장 & 큐 등록
    @Transactional
    public OrderRes createOrder(OrderReq orderReq) throws JsonProcessingException {

        OrderEntity savedOrder = orderRepository.save(modelMapper.map(orderReq, OrderEntity.class));
        orderRepository.flush();
        //JPA에서 save() 후 엔티티는 영속 상태(Managed)로 유지
        //flush()를 호출하면 DB에 즉시 insert/update 쿼리 실행 → DB-generated 값(auto-increment ID 등)도 엔티티에 반영
        //즉, 엔티티와 DB 값이 동기화
        String orderJson = objectMapper.writeValueAsString(savedOrder);
        redisTemplate.opsForList().rightPush("orderQueue", orderJson);

        return  modelMapper.map(savedOrder, OrderRes.class);
    }


}

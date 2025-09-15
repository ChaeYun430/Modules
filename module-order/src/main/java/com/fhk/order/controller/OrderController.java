package com.fhk.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fhk.order.dto.OrderReq;
import com.fhk.order.dto.OrderRes;
import com.fhk.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderRes> createOrder(@RequestBody OrderReq orderReq) throws JsonProcessingException {

        OrderRes orderRes = orderService.createOrder(orderReq);

        return ResponseEntity.ok(orderRes);
    }

}

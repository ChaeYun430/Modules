package com.fhk.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fhk.core.dto.OrderReq;
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

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderReq orderReq) throws JsonProcessingException {




        return ResponseEntity.ok("Order received");
    }

}

package module.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import module.order.dto.OrderReq;
import module.order.dto.OrderRes;
import module.order.service.OrderService;
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

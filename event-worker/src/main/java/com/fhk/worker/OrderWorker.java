package com.fhk.worker;

import com.fhk.pay.dto.PayReq;
import com.fhk.inventory.service.InventoryService;
import com.fhk.pay.service.PaymentService;
import com.fhk.outbox.constant.OrderStatus;
import com.fhk.outbox.dto.EventDTO;
import com.fhk.outbox.dto.OrderDTO;
import com.fhk.outbox.service.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderWorker {

    private final RedisTemplate<String, OrderDTO> redisTemplate;
    private final EventPublisher eventPublisher;
    private final PaymentService paymentService;
    private final InventoryService inventoryService;

    synchronized void processPaymentQueue(String queueKey, String orderId, OrderDTO message) {

        //FIFO 추출
        while ((message = redisTemplate.opsForList().leftPop(queueKey)) != null) {

            //비즈니스 로직 실행
            PayReq payReq = PayReq.builder()
                    .orderId(orderId)
                    .amount(message.getAmount())
                    .build();
            paymentService.createPayment(payReq);

            //상태 기록 결과 발행
            EventDTO<Object> outboxDTO = EventDTO.builder()
                    .aggregateType(OrderStatus.PAID)
                    .aggregateId(message.getOrderId())
                    .topicName("")
                    .payload(message)
                    .build();
            eventPublisher.publishEvent(outboxDTO);
        }
        // 장애 복구
    }

    synchronized void processInventoryQueue(String queueKey, String orderId, OrderDTO message) {

        //FIFO 추출
        while ((message = redisTemplate.opsForList().leftPop(queueKey)) != null) {

            //비즈니스 로직 실행


            //상태 기록 결과 발행
            EventDTO<Object> outboxDTO = EventDTO.builder()
                    .aggregateType(null)
                    .aggregateId(message.getOrderId())
                    .topicName("")
                    .payload(message)
                    .build();
            eventPublisher.publishEvent(outboxDTO);
        }
        // 장애 복구
    }
}

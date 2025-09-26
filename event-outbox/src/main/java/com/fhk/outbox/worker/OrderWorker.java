package com.fhk.outbox.worker;

import com.fhk.core.constant.OrderStatus;
import com.fhk.core.dto.EventDTO;
import com.fhk.core.dto.OrderDTO;
import com.fhk.core.service.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderWorker {

    private final RedisTemplate<String, OrderDTO> orderRedisTemplate;
    private final EventPublisher eventPublisher;
    private final PaymentService paymentService;


    public synchronized void processPaymentQueue(String queueKey, String orderId, OrderDTO message) {

        //FIFO 추출
        while ((message = orderRedisTemplate.opsForList().leftPop(queueKey)) != null) {

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


}

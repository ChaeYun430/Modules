package module.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhk.outbox.dto.OrderDTO;
import com.fhk.outbox.dto.EventDTO;
import com.fhk.outbox.service.EventPublisher;
import com.fhk.outbox.constant.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
@Import(EventPublisher.class)
public class OrderBatch {    //배치 추출 + Kafka 전송

    private final RedisTemplate<String, String> redisTemplate;
    private final EventPublisher eventPublisher;
    private final ObjectMapper objectMapper;
    private final List<NewTopic> newTopics;

    @Value("${batch.name}")
    private String redisKey;

    private final int trafficPoint = 5;


    //1초마다 5개씩 보낸다.
    @Scheduled(fixedRate = 1000)
    public void batchBySize() throws JsonProcessingException {
        List<String> batch = new ArrayList<>();
        Long queueSize = redisTemplate.opsForList().size(redisKey);
        if (queueSize == null || queueSize == 0) {
            return;
        }else if (queueSize < trafficPoint) {
            return;
        }else {
            for (int i = 0; i < queueSize; i++) {
                String messageJason = redisTemplate.opsForList().leftPop(redisKey);
                batch.add(messageJason);
            }
        }
        produceOrder(batch);
        // 처리 후 Redis에서 제거
        redisTemplate.opsForList().trim(redisKey, queueSize, queueSize-1);
    }


    //5분마다 얼마나?? 다 지울 수는 없잖음...
    @Scheduled(cron = "0 0/5 * * * *")
    public void batchByPeriod() throws JsonProcessingException {
        List<String> batch = new ArrayList<>();
        Long queueSize = redisTemplate.opsForList().size(redisKey);
        if (queueSize == null || queueSize == 0) return;
        for (int i = 0; i < queueSize; i++) {
            String messageJason = redisTemplate.opsForList().leftPop(redisKey);
            batch.add(messageJason);
        }
        produceOrder(batch);
        // 처리 후 Redis 비우기
        redisTemplate.delete(redisKey);
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

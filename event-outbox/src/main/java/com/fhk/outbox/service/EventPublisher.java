package com.fhk.outbox.service;

import com.fhk.outbox.repository.OutboxRepository;
import com.fhk.outbox.domain.OutboxEntity;
import com.fhk.core.dto.EventDTO;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ModelMapper modelMapper;

    @Transactional
    public void publishEvent(EventDTO event) {
        OutboxEntity outboxEntity = modelMapper.map(event, OutboxEntity.class);
        outboxRepository.save(outboxEntity);  // JPA가 중복 키 오류 발생 시 예외

        kafkaTemplate.send(event.getTopicName(), event.getAggregateId(),event.getPayload());
        kafkaTemplate.flush();
    }

    @Transactional
    public void processPendingEvents(int maxRetry) {    //아직 처리되지 않은 이벤트를 찾아 실제로 발행/처리하는 역할
        List<OutboxEntity> events = outboxRepository.findByProcessedFalseAndRetryCountLessThanOrderByCreatedAtAsc(maxRetry);
        for (OutboxEntity event : events) {
            try {
                // Kafka 발행, Redis 갱신 등 처리 로직
                outboxRepository.save(event.completePendingEvent());
            } catch (Exception ex) {
                outboxRepository.save(event.failPendingEvent());
            }
        }
    }
}

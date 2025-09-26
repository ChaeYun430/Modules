package com.fhk.outbox.service;

import com.fhk.core.service.EventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final EventPublisher outboxService;

    @Scheduled(fixedDelay = 5000)
    public void processOutbox() {
        outboxService.processPendingEvents(5);  // 최대 5회 재처리
    }

}


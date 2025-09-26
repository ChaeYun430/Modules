package com.fhk.core.service;

import com.fhk.core.dto.EventDTO;

public interface EventPublisher {

    public void publishEvent(EventDTO event);

    public void processPendingEvents(int maxRetry);
}

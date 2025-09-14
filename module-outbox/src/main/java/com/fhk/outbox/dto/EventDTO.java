package com.fhk.outbox.dto;

import com.fhk.outbox.constant.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventDTO<T> {

    private OrderStatus aggregateType;

    private String aggregateId;

    private String topicName;

    private T payload;

}

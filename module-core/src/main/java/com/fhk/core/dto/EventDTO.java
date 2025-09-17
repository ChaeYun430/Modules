package com.fhk.core.dto;


import com.fhk.core.constant.OrderStatus;
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

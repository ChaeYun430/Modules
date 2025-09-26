package com.fhk.core.dto;


import com.fhk.core.constant.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class EventDTO<T> {

    private OrderStatus aggregateType;

    private String aggregateId;

    private String topicName;

    private T payload;

}

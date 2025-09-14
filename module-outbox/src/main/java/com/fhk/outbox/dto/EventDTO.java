package com.fhk.outbox.dto;

import com.fhk.outbox.constant.AggregateType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventDTO {

    private AggregateType aggregateType;

    private String aggregateId;

    private String topicName;

    private String payload;

}

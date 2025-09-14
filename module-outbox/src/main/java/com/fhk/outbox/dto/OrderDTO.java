package com.fhk.outbox.dto;

import com.fhk.outbox.constant.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDTO {

    private String orderId;

    private String consumer;

    private String merchant;

    private Long amount;

    private OrderStatus status;
}

package com.fhk.module.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDTO {

    private String orderId;

    private String consumer;

    private String merchant;

    private Long amount;

    private String status;
}

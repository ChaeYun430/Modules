package com.fhk.module.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderMessage {

    private String orderId;

    private String consumer;

    private String merchant;
}

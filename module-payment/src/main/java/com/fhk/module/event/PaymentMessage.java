package com.fhk.module.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentMessage {


    private String orderId;

    private long amount;


}

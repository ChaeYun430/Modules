package com.fhk.pay.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentDTO {

    private String paymentId;

    private String orderId;

    private long amount;

}

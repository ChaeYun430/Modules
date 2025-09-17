package com.fhk.order.paymentService;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PayReq {

    private String orderId;

    private Long amount;

}

package com.fhk.module.modulepgclient.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PgReq {

    // confirm
    private String paymentKey;  //결제의 키값
    private String orderId; //주문번호
    private int amount; //결제할 금액

  /*  // payments 택1
    private String paymentKey;  //결제의 키값
    private String orderId; //주문번호

    // cancel
    private String paymentKey;  //결제의 키값

    // virtual-accounts
    private long amount;         // 결제 금액
    private String orderId;      // 주문 ID
    private String orderName;    // 주문명
    private String customerName; // 고객 이름
    private String bank;         // 은행 코드*/

}

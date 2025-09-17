package com.fhk.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRes {

    private String orderId;

    private String userId;

    private String orderDate;

    private String orderStatus;

}

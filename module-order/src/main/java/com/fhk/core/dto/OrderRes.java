package com.fhk.core.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderRes {

    private String orderId;

    private String userId;

    private String orderDate;

    private String orderStatus;

}

package com.fhk.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRes {

    private String orderId;

    private String consumer;

    private LocalDate orderDate;

    private String orderStatus;

}

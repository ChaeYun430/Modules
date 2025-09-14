package com.fhk.module.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderReq {

    private String consumer;

    private String merchant;

    private Long totalAmount;
}

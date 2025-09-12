package com.fhk.module.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class OrderReq {

    private String consumer;

    private String merchant;

    private Long totalAmount;
}

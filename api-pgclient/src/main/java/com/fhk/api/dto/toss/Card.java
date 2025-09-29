package com.fhk.api.dto.toss;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Card {

    private String cardCompany;
    private String cardNumber;
    private String owner;
    private String installmentPlanType;
    private Integer interestFree;
    private String approveNo;
    private LocalDateTime approvedAt;

}

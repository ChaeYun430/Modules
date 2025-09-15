package com.fhk.api.dto.toss;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Transfer {

    private String bankCode;
    private String accountNumber;
    private String depositor;
    private LocalDateTime transferBankApprovedAt;

}

package com.fhk.core.modulepgclient.dto.toss;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class EasyPay {

    private String provider;
    private String easyPayTransactionId;
    private LocalDateTime approvedAt;

}

package com.fhk.module.modulepgclient.dto.toss;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class VirtualAccount {

    private String accountNumber;
    private String bank;
    private String customerName;
    private LocalDateTime dueDate;

}

package com.fhk.module.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_table")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String paymentId;

    @Column(name = "orderId", nullable = false)
    private String orderId;

    @Column(name = "amount", nullable = false)
    private long amount;


}

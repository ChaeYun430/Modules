package com.fhk.order.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_table")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;

    private String consumer;

    private String merchant;

    private Long amount;

    private String status; // CREATED, PAID, CANCELLED

    @PrePersist
    protected void prePersist() {
        this.status = "CREATED";
    }

}
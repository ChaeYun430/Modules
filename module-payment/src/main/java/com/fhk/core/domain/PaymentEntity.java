package com.fhk.core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //JPA 기본 생성자 보호
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false, unique = true)     // 주문 ID (외부/내부 주문과 매핑)
    private String orderId;

    @Column(nullable = false, unique = true)     // PG사 결제 키
    private String pgKey;

    @Column(nullable = false)   // 결제 금액
    private int amount;

    @Column(nullable = false)    // 결제 상태 (READY, DONE, CANCELLED, REFUND 등)
    private String status;

    private LocalDateTime approvedAt;    // 결제 완료 시간

    @Column(updatable = false)
    private LocalDateTime createdAt;    // 생성/수정 시간 자동 기록

    private LocalDateTime updatedAt;

    // 엔티티가 처음 저장되기 직전에 실행되는 메서드
    @PrePersist
    public void prePersist() {
        this.status = "READY";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 정적 팩토리 메서드로 객체 생성
    public static PaymentEntity create(String orderId, int amount) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.orderId = orderId;
        paymentEntity.amount = amount;
        return paymentEntity;
    }

    //객체 생성 의도 명확화
    //기본값 자동 설정
    //불변성/안전성 확보


}

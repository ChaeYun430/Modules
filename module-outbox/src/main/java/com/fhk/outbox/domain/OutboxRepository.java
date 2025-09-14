package com.fhk.outbox.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepository extends JpaRepository<OutboxEntity, Long> {


    List<OutboxEntity> findByProcessedFalseAndRetryCountLessThanOrderByCreatedAtAsc(int maxRetry);
}




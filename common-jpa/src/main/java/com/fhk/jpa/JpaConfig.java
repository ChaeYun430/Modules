package com.fhk.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
    // 공통 JPA 설정 (Auditing, 엔티티 스캔 등)


}
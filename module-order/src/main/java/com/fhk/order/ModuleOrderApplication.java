package com.fhk.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

//
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = "com.fhk.order")
@EntityScan(basePackages = {
        "com.fhk.order.domain",
        "com.fhk.outbox.domain",
})
@EnableJpaRepositories(basePackages = {
        "com.fhk.order.repository",
        "com.fhk.outbox.repository",
})
public class ModuleOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleOrderApplication.class, args);
    }

}

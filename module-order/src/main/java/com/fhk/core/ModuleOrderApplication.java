package com.fhk.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ModuleOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleOrderApplication.class, args);
    }

}

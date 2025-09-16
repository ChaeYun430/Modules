package com.fhk.outbox;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackages = "com.fhk.outbox.service")
@ComponentScan(basePackages = "com.fhk.outbox.repository")
public class OutboxAutoConfig {

}



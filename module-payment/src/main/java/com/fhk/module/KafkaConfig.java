package com.fhk.module;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
@EnableKafka
//@KafkaListener가 붙은 메서드를 Spring이 스캔하고, 백그라운드 Kafka 컨슈머 컨테이너를 생성하도록함.
//Listener Container가 자동으로 Kafka 브로커와 연결됨
public class KafkaConfig {

    // Consumer → @KafkaListener
    //Spring은 ConcurrentKafkaListenerContainerFactory를 통해 Listener를 관리
    //트랜잭션, ack 모드, concurrency 설정 등 적용 가능
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setConcurrency(3);
        return factory;
    }

}

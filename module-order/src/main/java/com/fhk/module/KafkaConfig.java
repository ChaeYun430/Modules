package com.fhk.module;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    //이름은 기본적으로 메서드명(producerFactory)이 Bean 이름
    @Bean
    public ProducerFactory<String, Object> producerFactory(KafkaProperties props) {
        Map<String, Object> configs = new HashMap<>(props.buildProducerProperties());
        configs.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "tx-order-");
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    //==================컨테이너 팩토리 사용========================

    // Producer → KafkaTemplate
    //KafkaTemplate Bean 생성 시 생성자 파라미터로 ProducerFactory를 주입
    //Spring이 컨테이너에서 ProducerFactory<String, Object> Bean을 찾아 자동으로 넣어줌
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("order-topic")
                .partitions(3)
                .replicas(2)
                .config(
                        TopicConfig.RETENTION_MS_CONFIG,
                        String.valueOf(7 * 24 * 60 * 60 * 1000L)
                )
                .build();
    }
}
    //- 일반적으로 간단한 설정은 yaml 파일을 사용하고, 복잡한 로직이나 타입 안정성이 중요한 설정은 Java Configuration을 사용

    //- yaml 파일
    // 가독성이 좋고 계층 구조가 직관적
    // 동적 설정에 대해서는 제한적이거나 타입의 안정성이 상대적으로 떨어진다

    //- Java Configuration 방식
    // 타입에 대한 안정성이 보장되고, IDE 내에서 자동완성을 지원
    // 상대적으로 많은 코드가 필요하고, 가독성이 낮다
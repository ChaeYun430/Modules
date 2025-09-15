package com.fhk.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableKafka
//@KafkaListener가 붙은 메서드를 Spring이 스캔하고, 백그라운드 Kafka 컨슈머 컨테이너를 생성하도록함.
//Listener Container가 자동으로 Kafka 브로커와 연결됨
public class KafkaConfig {

    //이름은 기본적으로 메서드명(producerFactory)이 Bean 이름
    @Bean
    public ProducerFactory<String, Object> producerFactory(KafkaProperties props) {
        Map<String, Object> config = new HashMap<>(props.buildProducerProperties());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public ConsumerFactory<String, Object> paymentFactory(KafkaProperties props) {
        JsonDeserializer<Object> deserializer = new JsonDeserializer<>(Object.class);

        Map<String, Object> config = new HashMap<>(props.buildProducerProperties());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    // properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

    //==================컨테이너 팩토리 사용========================

    // Producer → KafkaTemplate
    //KafkaTemplate Bean 생성 시 생성자 파라미터로 ProducerFactory를 주입
    //Spring이 컨테이너에서 ProducerFactory<String, Object> Bean을 찾아 자동으로 넣어줌
    @Bean
    @ConditionalOnMissingBean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> factory) {
        return new KafkaTemplate<>(factory);
    }


    // Consumer → @KafkaListener
    //Spring은 ConcurrentKafkaListenerContainerFactory를 통해 Listener를 관리
    //트랜잭션, ack 모드, concurrency 설정 등 적용 가능
    @Bean
    @ConditionalOnMissingBean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(ConsumerFactory<String, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setConcurrency(3);
        return factory;
    }

    @Bean
    public List<NewTopic> topics() {
        return List.of(

                TopicBuilder.name("order-topic").partitions(3).replicas(1)
                        .config(
                        TopicConfig.RETENTION_MS_CONFIG,
                        String.valueOf(7 * 24 * 60 * 60 * 1000L)).build(),
                TopicBuilder.name("payment-topic").partitions(3).replicas(1).build(),
                TopicBuilder.name("accounting-topic").partitions(3).replicas(1).build(),
                TopicBuilder.name("notification-topic").partitions(3).replicas(1).build()

        );
    }
}
    //- 일반적으로 간단한 설정은 yaml 파일을 사용하고, 복잡한 로직이나 타입 안정성이 중요한 설정은 Java Configuration을 사용

    //- yaml 파일
    // 가독성이 좋고 계층 구조가 직관적
    // 동적 설정에 대해서는 제한적이거나 타입의 안정성이 상대적으로 떨어진다

    //- Java Configuration 방식
    // 타입에 대한 안정성이 보장되고, IDE 내에서 자동완성을 지원
    // 상대적으로 많은 코드가 필요하고, 가독성이 낮다
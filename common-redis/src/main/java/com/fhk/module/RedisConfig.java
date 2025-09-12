package com.fhk.module;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // 1. application.yaml → cluster 노드 정의
    //cluster 노드 정의만 되어 있으면, 클러스터 인식과 연결은 자동으로 처리
    //개발자가 "이 노드에 저장해라"라고 지정하는 게 아니라, 키 값 자체가 어느 슬롯(=노드)로 갈지 정해버립니다.

    // 2. RedisTemplate만 커스터마이징해서 key/value/hash 직렬화 방식 통일
    // Spring Data Redis의 기본값은 JdkSerializationRedisSerializer
    // Redis Hash = 메인 키 아래 속성들을 가진 작은 객체
    // 공통 모듈 특성에 맞춰
    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

}


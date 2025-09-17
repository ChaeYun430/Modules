package com.fhk.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "redis")
@Getter
@Setter
public class CommonRedisProperties {

    private List<String> clusterNodes = new ArrayList<>();

    private String host;
    private int port;
}
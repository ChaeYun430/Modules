package com.fhk.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;
import java.time.Duration;

@Setter
@Getter
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisClusterProperties {

    private Duration timeout;

    private Cluster cluster;

    @Setter
    @Getter
    public static class Cluster {

        private List<String> nodes;

    }

}

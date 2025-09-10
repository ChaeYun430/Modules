package conventions

import org.gradle.api.Plugin
import org.gradle.api.Project

class RedisConventionsPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.plugins.apply('org.springframework.boot')

        project.dependencies {
            implementation 'org.springframework.boot:spring-boot-starter-data-redis'
            implementation 'redis.clients:jedis:5.0.0'
        }
    }
}

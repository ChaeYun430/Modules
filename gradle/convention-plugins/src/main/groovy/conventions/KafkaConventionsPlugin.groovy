

import org.gradle.api.Plugin
import org.gradle.api.Project

class KafkaConventionsPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.plugins.apply('org.springframework.boot')

        project.dependencies {
            implementation 'org.springframework.kafka:spring-kafka'
            testImplementation 'org.springframework.kafka:spring-kafka-test'
        }
    }
}

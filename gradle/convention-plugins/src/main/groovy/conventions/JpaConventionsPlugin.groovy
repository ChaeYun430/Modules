package conventions

import org.gradle.api.Plugin
import org.gradle.api.Project

class JpaConventionsPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.plugins.apply('org.springframework.boot')
        project.plugins.apply('io.spring.dependency-management')
        project.plugins.apply('java')

        project.dependencies {
            implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
            runtimeOnly 'com.oracle.database.jdbc:ojdbc11'
            implementation 'org.projectlombok:lombok'
            annotationProcessor 'org.projectlombok:lombok'
        }

        project.tasks.withType(JavaCompile).configureEach {
            options.encoding = 'UTF-8'
            options.compilerArgs << '-parameters'
        }
    }
}

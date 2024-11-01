package com.report.ro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.report.ro")
@EntityScan("com.report.ro.model")
@EnableJpaRepositories("com.report.ro.repository")
public class RoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoApplication.class, args);
    }
}
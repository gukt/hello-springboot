package com.example.hellospringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

    public static void main(String[] args) {
        // 将 System Properties 都打印出来
        // System.getProperties().forEach((k, v) -> System.out.println(k + ": " + v));

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        // 将 Spring Boot 的 Environment 都打印出来
        // context.getEnvironment().getPropertySources().forEach(System.out::println);

        // 打印出 LOG_DATEFORMAT_PATTERN 的值
        // System.out.println(context.getEnvironment().getProperty("LOG_DATEFORMAT_PATTERN"));
    }

}

package com.dm4nk.aoptest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.dm4nk.aop",
                "com.dm4nk.aoptest"
        }
)

public class AopTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(AopTestApplication.class, args);
    }
}

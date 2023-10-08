package com.dm4nk.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {"com.dm4nk.customer", "com.dm4nk.amqp", "com.dm4nk.aop"}
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.dm4nk.clients"
)
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}

package com.dm4nk.customer.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {
                "com.dm4nk.customer",
                "com.dm4nk.amqp",
                "com.dm4nk.aop.logger",
        }
)
@EnableJpaRepositories(
        basePackages = "com.dm4nk.customer.db.repository"
)
@EntityScan(
        basePackages = "com.dm4nk.customer.db.model"
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.dm4nk.clients"
)
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}

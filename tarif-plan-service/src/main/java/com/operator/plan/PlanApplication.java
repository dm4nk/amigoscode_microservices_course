package com.operator.plan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(
        scanBasePackages = {
                "com.operator.plan",
        }
)
@EnableFeignClients(
        basePackages = "com.operator.clients"
)
@EnableKafka
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class PlanApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlanApplication.class, args);
    }
}

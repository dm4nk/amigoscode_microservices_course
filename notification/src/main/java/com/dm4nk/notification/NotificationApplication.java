package com.dm4nk.notification;

import com.dm4nk.amqp.RabbitMQMessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(
        scanBasePackages = {
                "com.dm4nk.notification",
                "com.dm4nk.amqp",
                "com.dm4nk.aop.logger",
        }
)
@EnableEurekaClient
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    // To run rabbitmq demo, just uncomment @Bean
    // @Bean
    CommandLineRunner commandLineRunner(RabbitMQMessageProducer producer, NotificationConfig notificationConfig) {
        return args -> producer.publish(
                notificationConfig.getInternalExchange(),
                notificationConfig.getInternalNotificationRoutingKey(),
                new Person("Dmitrii", 22)
        );
    }

    public record Person(String name, int age) {
    }
}

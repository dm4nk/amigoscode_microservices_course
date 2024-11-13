package com.dm4nk.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(
        scanBasePackages = {
                "com.dm4nk.search",
                "com.dm4nk.aop.logger",
        }
)
@EnableElasticsearchRepositories(
        basePackages = "com.dm4nk.search.repository"
)
@EnableFeignClients(
        basePackages = "com.dm4nk.clients"
)
@EnableKafka
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}

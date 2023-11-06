package com.dm4nk.ban.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(
        scanBasePackages = {
                "com.dm4nk.ban",
                "com.dm4nk.aop.logger",
                "com.dm4nk.ban.db.model.jooq.generated.tables",
        }
)
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
@EnableConfigurationProperties
@EnableTransactionManagement
public class BanApplication {
    public static void main(String[] args) {
        SpringApplication.run(BanApplication.class, args);
    }
}

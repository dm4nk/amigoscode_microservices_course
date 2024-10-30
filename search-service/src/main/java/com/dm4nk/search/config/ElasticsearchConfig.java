package com.dm4nk.search.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({
        ElasticsearchProperties.class,
})
public class ElasticsearchConfig extends ElasticsearchConfiguration {
    private final ElasticsearchProperties elasticsearchProperties;

    @Override
    public ClientConfiguration clientConfiguration() {
        ClientConfiguration.MaybeSecureClientConfigurationBuilder configurationBuilder = ClientConfiguration.builder()
                .connectedTo(elasticsearchProperties.getHostAndPort());
        return configurationBuilder.build();
    }
}

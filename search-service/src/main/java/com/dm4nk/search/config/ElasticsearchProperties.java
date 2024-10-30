package com.dm4nk.search.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Параметры интеграции с Elasticsearch.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchProperties {

    private String hostAndPort;
    private String customerIndexName;
}

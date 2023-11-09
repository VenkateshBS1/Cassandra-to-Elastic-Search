package com.ivoyant.MappingCassandraToElasticSearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.ivoyant.MappingCassandraToElasticSearch")
public class ElasticSearchConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchConfig.class);

    @Value("${elastic.clusterName}")
    private String clusterName;

    @Value("${elastic.port}")
    private String port;

    @Value("${elastic.scheme}")
    private String scheme;

    @Bean
    public RestHighLevelClient getClient() {
        RestHighLevelClient client = null;
        try {
            client = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(clusterName, Integer.parseInt(port), scheme))
            );
            LOGGER.info("Connected to ES Cluster");
        } catch (Exception e) {
            LOGGER.error("Unable to Connect to ES Cluster : {}", e.getMessage());
        }
        return client;
    }
}



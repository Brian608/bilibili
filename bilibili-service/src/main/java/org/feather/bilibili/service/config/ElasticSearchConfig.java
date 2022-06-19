package org.feather.bilibili.service.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @projectName: bilibili
 * @package: org.feather.bilibili.service.config
 * @className: ExlsticSearchConfig
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/18 22:04
 * @version: 1.0
 */
@Configuration
public class ElasticSearchConfig  extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.rest.uri}")
    private String esUrl;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
     final    ClientConfiguration clientConfiguration=ClientConfiguration.builder().connectedTo(esUrl).build();
        return RestClients.create(clientConfiguration).rest();
    }
}

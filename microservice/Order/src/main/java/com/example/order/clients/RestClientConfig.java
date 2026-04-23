package com.example.order.clients;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Bean
    @LoadBalanced
    public RestClient.Builder productRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    @Primary
    public RestClient.Builder restClientBuilderP() {
        return RestClient.builder();
    }
}

package com.cfb.facts.cfbfunfacts.Configs;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    @Bean
    public HttpClient buildHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10 * 1000).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    }
}

package com.sdll18.leetcode.spider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-06
 */
@Configuration
public class RestTemplateConfiguration {

    @Bean
    public ClientHttpRequestFactory requestFactory() throws Exception {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(10000);
        requestFactory.setConnectTimeout(5000);
        return requestFactory;
    }

    @Bean
    public RestTemplate restTemplate() throws Exception {
        RestTemplate restTemplate = new RestTemplate(requestFactory());
        return restTemplate;
    }
}

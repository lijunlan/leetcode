package com.sdll18.leetcode.spider.config;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-06
 */
@Configuration
public class MongoConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public MongoClient mongoClient() throws Exception {
        List<ServerAddress> seeds = new ArrayList<>(dataSource.getReplicaSet().size());
        for (String rs : dataSource.getReplicaSet()) {
            String[] adr = rs.split(":");
            ServerAddress address = new ServerAddress(adr[0], Integer.valueOf(adr[1]));
            seeds.add(address);
        }
        List<MongoCredential> credentialsList = new ArrayList<>(1);
        credentialsList.add(MongoCredential.createCredential(dataSource.getUsername(), dataSource.getDb(), dataSource.getPassword().toCharArray()));
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().
                connectionsPerHost(20).
                threadsAllowedToBlockForConnectionMultiplier(10).
                connectTimeout(1000).
                maxWaitTime(1500).
                socketKeepAlive(true).
                socketTimeout(1500).writeConcern(WriteConcern.MAJORITY).build();
        MongoClient mongoClient = new MongoClient(seeds, credentialsList, mongoClientOptions);
        return mongoClient;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(mongoClient(), dataSource.getUserDb());
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}

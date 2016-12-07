package com.sdll18.leetcode.spider.config;

import com.mongodb.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-06
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.mongo")
public class MongoConfiguration {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    private List<String> replicaSet = new ArrayList<>();

    @NotNull
    @NotEmpty
    private String db;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setReplicaSet(List<String> replicaSet) {
        this.replicaSet = replicaSet;
    }

    public void setDb(String db) {
        this.db = db;
    }

    @Bean
    public MongoClientFactoryBean mongo() {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        MongoCredential credential = MongoCredential.createCredential(username, db, password.toCharArray());
        mongo.setCredentials(new MongoCredential[]{credential});
        ServerAddress[] addresses = new ServerAddress[replicaSet.size()];
        for (int i = 0; i < replicaSet.size(); i++) {
            String rs = replicaSet.get(i);
            ServerAddress address = new ServerAddress(rs.split(":")[0], Integer.valueOf(rs.split(":")[1]));
            addresses[i] = address;
        }
        mongo.setReplicaSetSeeds(addresses);
        return mongo;
    }

    @Bean
    public MongoClient mongoClient() throws Exception {
        List<ServerAddress> seeds = new ArrayList<>(replicaSet.size());
        for (String rs : replicaSet) {
            String[] adr = rs.split(":");
            ServerAddress address = new ServerAddress(adr[0], Integer.valueOf(adr[1]));
            seeds.add(address);
        }
        List<MongoCredential> credentialsList = new ArrayList<>(1);
        credentialsList.add(MongoCredential.createCredential(username, db, password.toCharArray()));
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
        return new SimpleMongoDbFactory(mongoClient(), db);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}

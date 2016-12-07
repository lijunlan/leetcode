package com.sdll18.leetcode.spider.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-07
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource.mongo", locations = "classpath:leetcode_spider.yml")
public class DataSource {

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

    @NotNull
    @NotEmpty
    private String userDb;

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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getReplicaSet() {
        return replicaSet;
    }

    public String getDb() {
        return db;
    }

    public String getUserDb() {
        return userDb;
    }

    public void setUserDb(String userDb) {
        this.userDb = userDb;
    }
}

package com.sdll18.leetcode.spider.dao.impl;

import com.sdll18.leetcode.spider.dao.BaseDao;
import com.sdll18.leetcode.spider.model.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-06
 */
public abstract class BaseMongoDaoImpl<T> implements BaseDao<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<T> find(Query query) {
        return getMongoTemplate().find(query, getEntityClass());
    }

    @Override
    public T findOne(Query query) {
        return getMongoTemplate().findOne(query, getEntityClass());
    }

    @Override
    public T update(Query query, Update update) {
        return getMongoTemplate().findAndModify(query, update, getEntityClass());
    }

    @Override
    public T save(T entity) {
        getMongoTemplate().save(entity);
        return entity;
    }

    @Override
    public T findById(String id) {
        return getMongoTemplate().findById(id, getEntityClass());
    }

    @Override
    public T findById(String id, String collectionName) {
        return getMongoTemplate().findById(id, getEntityClass(), collectionName);
    }

    @Override
    public Page<T> findPage(Page<T> page, Query query) {
        long count = this.count(query);
        page.setTotal(count);
        int pageSize = page.getPageEnd() - page.getPageStart();
        query.skip(page.getPageStart()).limit(pageSize);
        List<T> rows = this.find(query);
        page.setRows(rows);
        return page;
    }

    @Override
    public long count(Query query) {
        return mongoTemplate.count(query, getEntityClass());
    }

    abstract protected Class<T> getEntityClass();
}

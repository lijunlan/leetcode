package com.sdll18.leetcode.spider.dao;

import com.sdll18.leetcode.spider.model.page.Page;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-06
 */
public interface BaseDao<T> {

    /**
     * 通过条件查询实体(集合)
     *
     * @param query
     */
    List<T> find(Query query);

    /**
     * 通过一定的条件查询一个实体
     *
     * @param query
     * @return
     */
    T findOne(Query query);

    /**
     * 通过条件查询更新数据
     *
     * @param query
     * @param update
     * @return
     */
    T update(Query query, Update update);

    /**
     * 保存一个对象到mongodb
     *
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * 通过ID获取记录
     *
     * @param id
     * @return
     */
    T findById(String id);

    /**
     * 通过ID获取记录,并且指定了集合名(表的意思)
     *
     * @param id
     * @param collectionName 集合名
     * @return
     */
    T findById(String id, String collectionName);

    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    Page<T> findPage(Page<T> page, Query query);

    /**
     * 求数据总和
     *
     * @param query
     * @return
     */
    long count(Query query);
}

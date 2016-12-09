package com.sdll18.leetcode.spider.dao.impl;

import com.sdll18.leetcode.spider.dao.TaskDao;
import com.sdll18.leetcode.spider.model.Task;
import org.springframework.stereotype.Repository;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-09
 */
@Repository("taskMongoDao")
public class TaskMongoDaoImpl extends BaseMongoDaoImpl<Task> implements TaskDao {

    @Override
    protected Class<Task> getEntityClass() {
        return Task.class;
    }
}

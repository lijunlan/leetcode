package com.sdll18.leetcode.spider.dao.impl;

import com.sdll18.leetcode.spider.dao.ProblemDao;
import com.sdll18.leetcode.spider.model.Problem;
import org.springframework.stereotype.Repository;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-08
 */
@Repository("problemMongoDao")
public class ProblemMongoDaoImpl extends BaseMongoDaoImpl<Problem> implements ProblemDao {
    @Override
    protected Class<Problem> getEntityClass() {
        return Problem.class;
    }
}

package com.sdll18.leetcode.spider.dao.impl;

import com.sdll18.leetcode.spider.dao.VisitedProblemListDao;
import com.sdll18.leetcode.spider.model.VisitedProblemList;
import org.springframework.stereotype.Repository;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-07
 */
@Repository("visitedProblemListMongoDao")
public class VisitedProblemListMongoDaoImpl extends BaseMongoDaoImpl<VisitedProblemList> implements VisitedProblemListDao {
    @Override
    protected Class<VisitedProblemList> getEntityClass() {
        return VisitedProblemList.class;
    }
}

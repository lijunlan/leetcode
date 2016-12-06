package com.sdll18.leetcode.spider.dao.impl;

import com.sdll18.leetcode.spider.dao.ProblemListDao;
import com.sdll18.leetcode.spider.model.ProblemList;
import org.springframework.stereotype.Repository;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-06
 */
@Repository("problemListMongoDao")
public class ProblemListMongoDaoImpl extends BaseMongoDaoImpl<ProblemList> implements ProblemListDao{

    @Override
    protected Class<ProblemList> getEntityClass() {
        return ProblemList.class;
    }

}

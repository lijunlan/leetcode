package com.sdll18.leetcode.spider.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-06
 */
public interface ProblemListService {

    JSONObject saveProblemList(JSONObject jsonObject);

    JSONObject listProblemList(JSONObject jsonObject);

    JSONObject findProblemList(JSONObject jsonObject);

}

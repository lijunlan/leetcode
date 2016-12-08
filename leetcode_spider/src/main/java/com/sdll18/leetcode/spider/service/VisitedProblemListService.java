package com.sdll18.leetcode.spider.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-07
 */
public interface VisitedProblemListService {

    JSONObject isVisited(JSONObject jsonObject);

    JSONObject visited(JSONObject jsonObject);

    JSONObject deleteAll();

    JSONObject delete(JSONObject jsonObject);

    JSONObject listVisited(JSONObject jsonObject);

}

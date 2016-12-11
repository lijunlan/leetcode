package com.sdll18.leetcode.spider.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-08
 */
public interface CrawlerService {

    JSONObject crawlProblem(JSONObject head, JSONObject jsonObject);

    JSONObject crawlAllProblem(JSONObject head);

    JSONObject crawlProblemList();

}

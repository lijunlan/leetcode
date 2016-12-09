package com.sdll18.leetcode.spider.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-09
 */
public interface TaskService {

    JSONObject createTask(JSONObject jsonObject);

    JSONObject updateDoneCount(JSONObject jsonObject);

    JSONObject closeTask(JSONObject jsonObject);

    JSONObject listTask(JSONObject jsonObject);

}

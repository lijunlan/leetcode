package com.sdll18.leetcode.spider.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sdll18.leetcode.spider.constant.Code;
import com.sdll18.leetcode.spider.dao.TaskDao;
import com.sdll18.leetcode.spider.model.Task;
import com.sdll18.leetcode.spider.model.page.Page;
import com.sdll18.leetcode.spider.service.TaskService;
import com.sdll18.leetcode.spider.util.FastJsonUtil;
import com.sdll18.leetcode.spider.util.PageToJSONUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Locale;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-09
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = Logger.getLogger(TaskServiceImpl.class);

    @Autowired
    @Qualifier("taskMongoDao")
    private TaskDao taskDao;

    @Override
    public JSONObject createTask(JSONObject jsonObject) {
        try {
            Integer wholeCount = jsonObject.getInteger("wholeCount");
            Task task = new Task();
            task.setWholeCount(wholeCount);
            task.setDoneCount(0);
            task.setStatus(0);
            task.setStartTime(Calendar.getInstance(Locale.CHINA).getTime());
            task = taskDao.save(task);
            JSONObject r = new JSONObject();
            r.put("id", task.getId());
            return FastJsonUtil.success(r);
        } catch (Exception e) {
            logger.error("failed to create task", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }

    @Override
    public JSONObject updateDoneCount(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("id");
            Integer doneCount = jsonObject.getInteger("doneCount");
            Task task = taskDao.findById(id);
            if (task == null) {
                return FastJsonUtil.error(Code.ERROR_ID_NOT_EXISTED, "task id is not existed");
            }
            task.setDoneCount(doneCount);
            taskDao.save(task);
            return FastJsonUtil.success();
        } catch (Exception e) {
            logger.error("failed to update done count of task", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }

    @Override
    public JSONObject closeTask(JSONObject jsonObject) {
        try {
            Integer status = jsonObject.getInteger("status");
            String infoId = jsonObject.getString("id");
            Task task = taskDao.findById(infoId);
            if (task == null) {
                return FastJsonUtil.error(Code.ERROR_ID_NOT_EXISTED, "task id is not existed");
            }
            if (task.getStatus().intValue() != 0) {
                return FastJsonUtil.error(Code.ERROR_STATUS_WRONG, "task has been closed before");
            }
            task.setStatus(status);
            task.setEndTime(Calendar.getInstance(Locale.CHINA).getTime());
            taskDao.save(task);
            return FastJsonUtil.success();
        } catch (Exception e) {
            logger.error("failed to close task", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }

    @Override
    public JSONObject listTask(JSONObject jsonObject) {
        try {
            Integer start = jsonObject.getInteger("start");
            start = start == null ? 0 : start;
            Integer end = jsonObject.getInteger("end");
            end = end == null ? 20 : end;
            Query q = new Query();
            q.with(new Sort(new Sort.Order(Sort.Direction.DESC, "startTime")));
            Page<Task> page = new Page<>();
            page.setPageStart(start);
            page.setPageEnd(end);
            page = taskDao.findPage(page, q);
            return FastJsonUtil.success(PageToJSONUtil.getJSON(page));
        } catch (Exception e) {
            logger.error("failed to list task!", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }
}

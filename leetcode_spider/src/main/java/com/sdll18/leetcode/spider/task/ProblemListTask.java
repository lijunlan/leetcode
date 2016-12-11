package com.sdll18.leetcode.spider.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdll18.leetcode.spider.constant.Code;
import com.sdll18.leetcode.spider.model.Task;
import com.sdll18.leetcode.spider.model.VisitedProblemList;
import com.sdll18.leetcode.spider.service.CrawlerService;
import com.sdll18.leetcode.spider.service.ProblemListService;
import com.sdll18.leetcode.spider.service.TaskService;
import com.sdll18.leetcode.spider.service.VisitedProblemListService;
import com.sdll18.leetcode.spider.util.FastJsonUtil;
import com.sdll18.leetcode.spider.util.JudgeResultUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-06
 */
@Component
public class ProblemListTask {

    private static Logger logger = Logger.getLogger(ProblemListTask.class);

    @Autowired
    private CrawlerService crawlerService;

    @Autowired
    private ProblemListService problemListService;

    @Autowired
    private VisitedProblemListService visitedProblemListService;

    @Autowired
    private TaskService taskService;

    public void start(JSONObject head) {
        new Thread(() -> runTask(head)).start();
    }

    private void runTask(JSONObject head) {
        try {
            int successNumber = 0;
            int failedNumber = 0;
            int ignoreNumber = 0;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("start", 0);
            jsonObject.put("end", 500);
            JSONObject r = problemListService.listProblemList(jsonObject);
            if (JudgeResultUtil.getResult(r)) {
                JSONArray array = r.getJSONObject("data").getJSONArray("list");
                JSONObject d1 = new JSONObject();
                d1.put("wholeCount", array.size());
                JSONObject r1 = taskService.createTask(d1);
                if (!JudgeResultUtil.getResult(r1)) {
                    logger.error("taskService created error\n" + r1);
                    return;
                }
                String taskId = r1.getJSONObject("data").getString("id");
                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    JSONObject r2 = visitedProblemListService.isVisited(object);
                    if (JudgeResultUtil.getResult(r2)) {
                        if (r2.getJSONObject("data").getBoolean("visited")) {
                            ignoreNumber++;
                            continue;
                        }
                        JSONObject result = crawlerService.crawlProblem(head, object);
                        if (result.getIntValue("code") == Code.SUCCESS) {
                            VisitedProblemList visitedProblemList = new VisitedProblemList();
                            visitedProblemList.setNumber(object.getInteger("number"));
                            JSONObject r3 = visitedProblemListService.visited((JSONObject) JSON.toJSON(visitedProblemList));
                            if (!JudgeResultUtil.getResult(r3)) {
                                logger.error(r3);
                            }
                            successNumber++;
                        } else {
                            failedNumber++;
                        }
                    } else {
                        failedNumber++;
                        logger.error(r2);
                    }
                    JSONObject inData = new JSONObject();
                    inData.put("doneCount", i + 1);
                    inData.put("id", taskId);
                    taskService.updateDoneCount(inData);
                }
                JSONObject d2 = new JSONObject();
                d2.put("successNumber", successNumber);
                d2.put("failedNumber", failedNumber);
                d2.put("ignoreNumber", ignoreNumber);
                d2.put("id", taskId);
                d2.put("status", 1);
                taskService.closeTask(d2);
            } else {
                logger.error("listProblemList failed\n" + r);
            }
        } catch (Exception e) {
            logger.error("problem list task failed", e);
        }
    }
}

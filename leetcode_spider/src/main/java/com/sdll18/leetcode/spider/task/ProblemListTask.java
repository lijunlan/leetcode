package com.sdll18.leetcode.spider.task;

import com.alibaba.fastjson.JSONObject;
import com.sdll18.leetcode.spider.service.CrawlerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-06
 */
public class ProblemListTask {

    private static Logger logger = Logger.getLogger(ProblemListTask.class);

    @Autowired
    private CrawlerService crawlerService;

    public void listProblemList() {
        logger.info("list problem list started!");
        JSONObject r = crawlerService.crawlProblemList();
        logger.info("list problem list finished!");
        logger.info(r);
    }

    public void listProblem() {
        logger.info("list problem started!");
        JSONObject r = crawlerService.crawlAllProblem();
        logger.info("list problem finished!");
        logger.info(r);
    }

}

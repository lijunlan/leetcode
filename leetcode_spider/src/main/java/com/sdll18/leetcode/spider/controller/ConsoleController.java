package com.sdll18.leetcode.spider.controller;

import com.alibaba.fastjson.JSONObject;
import com.sdll18.leetcode.spider.constant.Code;
import com.sdll18.leetcode.spider.service.CrawlerService;
import com.sdll18.leetcode.spider.service.ProblemListService;
import com.sdll18.leetcode.spider.service.VisitedProblemListService;
import com.sdll18.leetcode.spider.task.ProblemListTask;
import com.sdll18.leetcode.spider.util.FastJsonUtil;
import com.sdll18.leetcode.spider.util.JudgeResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-08
 */
@Controller
@RequestMapping(path = "/console", produces = "application/json;charset=UTF-8")
public class ConsoleController {

    @Autowired
    private ProblemListService problemListService;

    @Autowired
    private CrawlerService crawlerService;

    @Autowired
    private VisitedProblemListService visitedProblemListService;

    @Autowired
    private ProblemListTask problemListTask;

    @RequestMapping(path = "/problemList/start", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject problemListStartAll() {
        return crawlerService.crawlProblemList();
    }

    @RequestMapping(path = "/problem/start", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject problemStartAll(@RequestBody JSONObject data) {
        if (data.getString("UserAgent") == null || data.getString("Cookie") == null)
            return FastJsonUtil.error(Code.ERROR_MISSING_PARAMETERS, "UserAgent or Cookie is not presented");
        problemListTask.start(data);
        return FastJsonUtil.success();
    }

    @RequestMapping(path = "/problem/start/{number}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject problemStart(@PathVariable Integer number,
                                   @RequestBody JSONObject data) {
        if (data.getString("UserAgent") == null || data.getString("Cookie") == null)
            return FastJsonUtil.error(Code.ERROR_MISSING_PARAMETERS, "UserAgent or Cookie is not presented");
        JSONObject inData = new JSONObject();
        inData.put("number", number);
        JSONObject r = problemListService.findProblemList(inData);
        if (JudgeResultUtil.getResult(r)) {
            JSONObject d1 = r.getJSONObject("data");
            JSONObject r2 = crawlerService.crawlProblem(data, d1);
            return r2;
        } else {
            return r;
        }
    }


    @RequestMapping(path = "/record", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject recordDeleteAll() {
        return visitedProblemListService.deleteAll();
    }

    @RequestMapping(path = "/record/{number}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject recordDelete(@PathVariable Integer number) {
        JSONObject inData = new JSONObject();
        inData.put("number", number);
        return visitedProblemListService.delete(inData);
    }

    @RequestMapping(path = "/record", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listRecord(@RequestParam(required = false) Boolean visited,
                                 @RequestParam(required = false) Integer start,
                                 @RequestParam(required = false) Integer end) {
        JSONObject inData = new JSONObject();
        inData.put("visited", visited);
        inData.put("start", start);
        inData.put("end", end);
        return visitedProblemListService.listVisited(inData);
    }

}

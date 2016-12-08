package com.sdll18.leetcode.spider.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdll18.leetcode.spider.constant.Code;
import com.sdll18.leetcode.spider.model.ProblemList;
import com.sdll18.leetcode.spider.model.VisitedProblemList;
import com.sdll18.leetcode.spider.service.CrawlerService;
import com.sdll18.leetcode.spider.service.ProblemListService;
import com.sdll18.leetcode.spider.service.ProblemService;
import com.sdll18.leetcode.spider.service.VisitedProblemListService;
import com.sdll18.leetcode.spider.util.FastJsonUtil;
import com.sdll18.leetcode.spider.util.JudgeResultUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-08
 */
@Service("crawlerService")
public class CrawlerServiceImpl implements CrawlerService {

    private static final Logger logger = Logger.getLogger(CrawlerServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProblemListService problemListService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private VisitedProblemListService visitedProblemListService;

    @Override
    public JSONObject crawlProblem(JSONObject jsonObject) {
        try {
            ProblemList problemList = JSON.toJavaObject(jsonObject, ProblemList.class);
            URL url = new URL("https://leetcode.com" + problemList.getPath());
            Document doc = Jsoup.parse(url, 3000);
            Element contentElement = doc.select("div.question-content").first();
            String content = contentElement.html();
            JSONObject inData = new JSONObject();
            inData.put("number", problemList.getNumber());
            inData.put("name", problemList.getName());
            inData.put("content", content);
            JSONObject result;
            do {
                result = problemService.saveProblem(inData);
            } while (result.getIntValue("code") == Code.ERROR_SAVE_FAILED);
            return result;
        } catch (Exception e) {
            logger.error("failed to visit problem!", e);
            logger.error(jsonObject);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }

    @Override
    public JSONObject crawlAllProblem() {
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
                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    JSONObject r2 = visitedProblemListService.isVisited(object);
                    if (JudgeResultUtil.getResult(r2)) {
                        if (r2.getJSONObject("data").getBoolean("visited")) {
                            ignoreNumber++;
                            continue;
                        }
                        JSONObject result = crawlProblem(object);
                        if (result.getIntValue("code") == Code.SUCCESS) {
                            successNumber++;
                            VisitedProblemList visitedProblemList = new VisitedProblemList();
                            visitedProblemList.setNumber(object.getInteger("number"));
                            JSONObject r3 = visitedProblemListService.visited((JSONObject) JSON.toJSON(visitedProblemList));
                            if (!JudgeResultUtil.getResult(r3)) {
                                logger.error(r3);
                            }
                        } else {
                            failedNumber++;
                        }
                    } else {
                        failedNumber++;
                        logger.error(r2);
                    }
                }
                JSONObject toReturn = new JSONObject();
                toReturn.put("successNumber", successNumber);
                toReturn.put("failedNumber", failedNumber);
                toReturn.put("ignoreNumber", ignoreNumber);
                return FastJsonUtil.success(toReturn);
            } else {
                return r;
            }
        } catch (Exception e) {
            logger.error("failed to crawAll problems!", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }

    @Override
    public JSONObject crawlProblemList() {
        try {
            ResponseEntity<String> entity = restTemplate.getForEntity("https://leetcode.com/api/problems/algorithms/", String.class);
            String content = entity.getBody();
            JSONObject jsonObject = JSON.parseObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray("stat_status_pairs");
            int successNumber = 0;
            int failedNumber = 0;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String name = object.getString("question__title");
                String path = "/problems/" + object.getString("question__title_slug");
                Integer qid = object.getInteger("question_id");
                ProblemList list = new ProblemList();
                list.setName(name);
                list.setNumber(qid);
                list.setPath(path);
                JSONObject result;
                do {
                    result = problemListService.saveProblemList((JSONObject) JSON.toJSON(list));
                } while (result.getIntValue("code") == Code.ERROR_SAVE_FAILED);
                if (result.getIntValue("code") == Code.SUCCESS) {
                    successNumber++;
                } else {
                    failedNumber++;
                }
            }
            JSONObject r = new JSONObject();
            r.put("successNumber", successNumber);
            r.put("failedNumber", failedNumber);
            return FastJsonUtil.success(r);
        } catch (Exception e) {
            logger.error("failed to visit problem list!", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }
}

package com.sdll18.leetcode.spider.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdll18.leetcode.spider.constant.Code;
import com.sdll18.leetcode.spider.dao.VisitedProblemListDao;
import com.sdll18.leetcode.spider.model.VisitedProblemList;
import com.sdll18.leetcode.spider.model.page.Page;
import com.sdll18.leetcode.spider.service.VisitedProblemListService;
import com.sdll18.leetcode.spider.util.FastJsonUtil;
import com.sdll18.leetcode.spider.util.PageToJSONUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Locale;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-07
 */
@Service("visitedProblemListService")
public class VisitedProblemListServiceImpl implements VisitedProblemListService {

    private static final Logger logger = Logger.getLogger(VisitedProblemListServiceImpl.class);

    @Autowired
    @Qualifier("visitedProblemListMongoDao")
    private VisitedProblemListDao visitedProblemListDao;

    @Override
    public JSONObject isVisited(JSONObject jsonObject) {
        try {
            Integer number = jsonObject.getInteger("number");
            VisitedProblemList visitedProblemList = visitedProblemListDao.findOne(new Query(Criteria.where("number").is(number)));
            if (visitedProblemList == null) {
                JSONObject r = new JSONObject();
                r.put("visited", false);
                return FastJsonUtil.success(r);
            } else {
                JSONObject r = new JSONObject();
                r.put("visited", true);
                return FastJsonUtil.success(r);
            }
        } catch (Exception e) {
            logger.error("find isVisited failed", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }

    @Override
    public JSONObject visited(JSONObject jsonObject) {
        try {
            VisitedProblemList visitedProblemList = JSON.toJavaObject(jsonObject, VisitedProblemList.class);
            VisitedProblemList old = visitedProblemListDao.findOne(new Query(Criteria.where("number").is(visitedProblemList.getNumber())));
            if (old != null) {
                visitedProblemList.setId(old.getId());
            }
            visitedProblemList.setUpdateTime(Calendar.getInstance(Locale.CHINA).getTime());
            visitedProblemListDao.save(visitedProblemList);
            return FastJsonUtil.success();
        } catch (Exception e) {
            logger.error("save visited failed", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }

    @Override
    public JSONObject deleteAll() {
        try {
            long count = visitedProblemListDao.delete(new Query());
            JSONObject r = new JSONObject();
            r.put("deleteNumber", count);
            return FastJsonUtil.success(r);
        } catch (Exception e) {
            logger.error("failed to delete all visited problem lists", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        try {
            Integer number = jsonObject.getInteger("number");
            long count = visitedProblemListDao.delete(new Query(Criteria.where("number").is(number)));
            JSONObject r = new JSONObject();
            r.put("deleteNumber", count);
            return FastJsonUtil.success(r);
        } catch (Exception e) {
            logger.error("failed to delete visited problem lists", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }

    @Override
    public JSONObject listVisited(JSONObject jsonObject) {
        try {
            Integer start = jsonObject.getInteger("start");
            start = start == null ? 0 : start;
            Integer end = jsonObject.getInteger("end");
            end = end == null ? 20 : end;
            Page<VisitedProblemList> page = new Page<>();
            page.setPageStart(start);
            page.setPageEnd(end);
            Query q = new Query();
            q.with(new Sort(new Sort.Order(Sort.Direction.DESC, "updateTime")));
            page = visitedProblemListDao.findPage(page, q);
            return FastJsonUtil.success(PageToJSONUtil.getJSON(page));
        } catch (Exception e) {
            logger.error("failed to list visited problem lists", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }
}

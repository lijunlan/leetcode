package com.sdll18.leetcode.spider.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sdll18.leetcode.spider.constant.Code;
import com.sdll18.leetcode.spider.dao.ProblemDao;
import com.sdll18.leetcode.spider.model.Problem;
import com.sdll18.leetcode.spider.model.page.Page;
import com.sdll18.leetcode.spider.service.ProblemService;
import com.sdll18.leetcode.spider.util.FastJsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Locale;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-08
 */
@Service("problemService")
public class ProblemServiceImpl implements ProblemService {

    private static final Logger logger = Logger.getLogger(ProblemServiceImpl.class);

    @Autowired
    @Qualifier("problemMongoDao")
    private ProblemDao problemDao;

    @Override
    public JSONObject saveProblem(JSONObject jsonObject) {
        try {
            Integer number = jsonObject.getInteger("number");
            String content = jsonObject.getString("content");
            String name = jsonObject.getString("name");
            Problem old = problemDao.findOne(new Query(Criteria.where("number").is(number)));
            if (old == null) {
                Problem problem = new Problem();
                problem.setVersion(1);
                problem.setUpdateTime(Calendar.getInstance(Locale.CHINA).getTime());
                problem.setContent(content);
                problem.setName(name);
                problem.setNumber(number);
                problem = problemDao.save(problem);
                JSONObject r = new JSONObject();
                r.put("id", problem.getId());
                r.put("version", problem.getVersion());
                return FastJsonUtil.success(r);
            } else {
                Query q = new Query();
                q.addCriteria(Criteria.where("number").is(number));
                q.addCriteria(Criteria.where("version").is(old.getVersion()));
                Update update = new Update();
                update.set("version", old.getVersion() + 1);
                update.set("content", content);
                update.set("name", name);
                update.set("updateTime", Calendar.getInstance(Locale.CHINA).getTime());
                Problem problem = problemDao.update(q, update);
                if (problem == null) {
                    return FastJsonUtil.error(Code.ERROR_SAVE_FAILED, "save problem failed, please retry");
                } else {
                    JSONObject r = new JSONObject();
                    r.put("id", problem.getId());
                    r.put("version", problem.getVersion());
                    return FastJsonUtil.success(r);
                }
            }
        } catch (Exception e) {
            logger.error("failed to save problem", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }

    @Override
    public JSONObject listProblem(JSONObject jsonObject) {
        try {
            Integer start = jsonObject.getInteger("start");
            start = start == null ? 0 : start;
            Integer end = jsonObject.getInteger("end");
            end = end == null ? 20 : end;
            Page<Problem> page = new Page<>();
            page.setPageStart(start);
            page.setPageEnd(end);
            Query q = new Query();
            q.with(new Sort(new Sort.Order(Sort.Direction.DESC, "updateTime")));
            page = problemDao.findPage(page, q);
            return FastJsonUtil.success(page.toJSON());
        } catch (Exception e) {
            logger.error("list problem failed", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }

    @Override
    public JSONObject findProblem(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("id");
            Integer number = jsonObject.getInteger("number");
            if (number != null) {
                Problem problem = problemDao.findOne(new Query(Criteria.where("number").is(number)));
                if (problem == null) {
                    return FastJsonUtil.error(Code.ERROR_ID_NOT_EXISTED, "problem number is not existed");
                }
                return FastJsonUtil.success(problem);
            } else {
                Problem problem = problemDao.findById(id);
                if (problem == null) {
                    return FastJsonUtil.error(Code.ERROR_ID_NOT_EXISTED, "problem id is not existed");
                }
                return FastJsonUtil.success(problem);
            }
        } catch (Exception e) {
            logger.error("failed to find problem", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }
}

package com.sdll18.leetcode.spider.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdll18.leetcode.spider.constant.Code;
import com.sdll18.leetcode.spider.dao.ProblemListDao;
import com.sdll18.leetcode.spider.model.ProblemList;
import com.sdll18.leetcode.spider.model.page.Page;
import com.sdll18.leetcode.spider.service.ProblemListService;
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
 * @Date: 2016-12-06
 */
@Service("problemListService")
public class ProblemListServiceImpl implements ProblemListService {

    private static Logger logger = Logger.getLogger(ProblemListServiceImpl.class);

    @Autowired
    @Qualifier("problemListMongoDao")
    private ProblemListDao problemListDao;

    @Override
    public JSONObject saveProblemList(JSONObject jsonObject) {
        try {
            ProblemList problemList = JSON.toJavaObject(jsonObject, ProblemList.class);
            Criteria criteria = Criteria.where("number").is(problemList.getNumber());
            ProblemList old = problemListDao.findOne(new Query(criteria));
            if (old != null) {
                Criteria c = new Criteria();
                c.andOperator(Criteria.where("_id").is(old.getId()), Criteria.where("version").is(old.getVersion()));
                Query q = new Query(c);
                Update update = new Update();
                update.set("name", problemList.getName());
                update.set("path", problemList.getPath());
                update.set("updateTime", problemList.getUpdateTime());
                update.set("version", old.getVersion() + 1);
                problemList = problemListDao.update(q, update);
                if (problemList == null) {
                    return FastJsonUtil.error(Code.ERROR_SAVE_FAILED, "save problem list failed, please retry");
                } else {
                    JSONObject r = new JSONObject();
                    r.put("version", problemList.getVersion());
                    r.put("id", problemList.getId());
                    return FastJsonUtil.success(r);
                }
            } else {
                problemList.setUpdateTime(Calendar.getInstance(Locale.CHINA).getTime());
                problemList.setVersion(1);
                problemList = problemListDao.save(problemList);
                JSONObject r = new JSONObject();
                r.put("version", problemList.getVersion());
                r.put("id", problemList.getId());
                return FastJsonUtil.success(r);
            }
        } catch (Exception e) {
            logger.error("save problem list failed", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }

    @Override
    public JSONObject listProblemList(JSONObject jsonObject) {
        try {
            Integer start = jsonObject.getInteger("start");
            start = start == null ? 0 : start;
            Integer end = jsonObject.getInteger("end");
            end = end == null ? 20 : end;
            Query query = new Query();
            query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "updateTime")));
            Page<ProblemList> page = new Page<>();
            page.setPageStart(start);
            page.setPageEnd(end);
            page = problemListDao.findPage(page, query);
            JSONObject r = page.toJSON();
            return FastJsonUtil.success(r);
        } catch (Exception e) {
            logger.error("list problem list failed", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }

    @Override
    public JSONObject findProblemList(JSONObject jsonObject) {
        try {
            Integer number = jsonObject.getInteger("number");
            String id = jsonObject.getString("id");
            if (id != null) {
                ProblemList problemList = problemListDao.findById(id);
                if (problemList == null) {
                    return FastJsonUtil.error(Code.ERROR_ID_NOT_EXISTED, "problem list id is not existed");
                }
                return FastJsonUtil.success(problemList);
            } else {
                Query query = new Query(Criteria.where("number").is(number));
                ProblemList problemList = problemListDao.findOne(query);
                if (problemList == null) {
                    return FastJsonUtil.error(Code.ERROR_ID_NOT_EXISTED, "problem list number is not existed");
                }
                return FastJsonUtil.success(problemList);
            }
        } catch (Exception e) {
            logger.error("find problem list failed", e);
            return FastJsonUtil.error(Code.ERROR_INTERNAL);
        }
    }
}

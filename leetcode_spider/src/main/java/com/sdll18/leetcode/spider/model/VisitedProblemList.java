package com.sdll18.leetcode.spider.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-07
 */
@Document(collection = "leetcode_visited_problem_list")
public class VisitedProblemList {

    @Id
    @JSONField
    private String id;

    @Field
    @JSONField
    private Integer number;

    @Field
    @JSONField
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "VisitedProblemList{" +
                "id='" + id + '\'' +
                ", number=" + number +
                ", updateTime=" + updateTime +
                '}';
    }
}

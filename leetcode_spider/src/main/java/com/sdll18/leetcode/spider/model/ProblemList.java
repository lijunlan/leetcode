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
 * @Date: 2016-12-06
 */
@Document(collection = "leetcode_problem_list")
public class ProblemList {

    @Id
    @JSONField
    private String id;

    @Field
    @JSONField
    private Integer number;

    @Field
    @JSONField
    private String name;

    @Field
    @JSONField
    private String path;

    @Field
    @JSONField
    private Integer version;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ProblemList{" +
                "id='" + id + '\'' +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", version=" + version +
                ", updateTime=" + updateTime +
                '}';
    }
}

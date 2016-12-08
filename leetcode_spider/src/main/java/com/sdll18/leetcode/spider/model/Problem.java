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
@Document(collection = "leetcode_problem")
public class Problem {

    @Id
    @JSONField
    private String id;

    @Field
    @JSONField
    private Integer number;

    @Field
    @JSONField
    private String content;

    @Field
    @JSONField
    private String name;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id='" + id + '\'' +
                ", number=" + number +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", version=" + version +
                ", updateTime=" + updateTime +
                '}';
    }
}

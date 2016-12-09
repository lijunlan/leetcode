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
 * @Date: 2016-12-09
 */
@Document(collection = "leetcode_task")
public class Task {

    @Id
    @JSONField
    private String id;

    @Field
    @JSONField
    private Date startTime;

    @Field
    @JSONField
    private Date endTime;

    @Field
    @JSONField
    private Integer successNumber;

    @Field
    @JSONField
    private Integer failedNumber;

    @Field
    @JSONField
    private Integer ignoreNumber;

    @Field
    @JSONField
    private Integer wholeCount;

    @Field
    @JSONField
    private Integer doneCount;

    @Field
    @JSONField
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(Integer successNumber) {
        this.successNumber = successNumber;
    }

    public Integer getFailedNumber() {
        return failedNumber;
    }

    public void setFailedNumber(Integer failedNumber) {
        this.failedNumber = failedNumber;
    }

    public Integer getIgnoreNumber() {
        return ignoreNumber;
    }

    public void setIgnoreNumber(Integer ignoreNumber) {
        this.ignoreNumber = ignoreNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWholeCount() {
        return wholeCount;
    }

    public void setWholeCount(Integer wholeCount) {
        this.wholeCount = wholeCount;
    }

    public Integer getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(Integer doneCount) {
        this.doneCount = doneCount;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", successNumber=" + successNumber +
                ", failedNumber=" + failedNumber +
                ", ignoreNumber=" + ignoreNumber +
                ", wholeCount=" + wholeCount +
                ", doneCount=" + doneCount +
                ", status=" + status +
                '}';
    }
}

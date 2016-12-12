package com.sdll18.leetcode.spider.controller.exception;

import com.alibaba.fastjson.JSONObject;
import com.sdll18.leetcode.spider.constant.Code;
import com.sdll18.leetcode.spider.util.FastJsonUtil;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-08
 */
@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = Logger.getLogger(ExceptionController.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSONObject handleException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return FastJsonUtil.error(Code.ERROR_INTERNAL, ex.getMessage());
    }
}

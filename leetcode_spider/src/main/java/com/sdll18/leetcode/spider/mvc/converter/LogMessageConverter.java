package com.sdll18.leetcode.spider.mvc.converter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.log4j.Logger;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-11-08
 */
public class LogMessageConverter extends FastJsonHttpMessageConverter {

    private static final Logger logger = Logger.getLogger(LogMessageConverter.class);

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Object data = super.readInternal(clazz, inputMessage);
        logger.info(String.format("Request Data is: %s", data));
        return data;
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        super.writeInternal(obj, outputMessage);
        logger.info(String.format("Response Data is: %s", obj));
    }
}

package com.sdll18.leetcode.spider.mvc.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.log4j.Logger;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = inputMessage.getBody();
        byte[] buf = new byte[1024];

        while (true) {
            int bytes = in.read(buf);
            if (bytes == -1) {
                byte[] bytes1 = baos.toByteArray();
                String inStr = new String(bytes1, "utf-8");
                logger.info(String.format("Request Data is: %s", inStr));
                return JSON.parseObject(bytes1, 0, bytes1.length, getCharset().newDecoder(), clazz, new Feature[0]);
            }

            if (bytes > 0) {
                baos.write(buf, 0, bytes);
            }
        }
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        super.writeInternal(obj, outputMessage);
        logger.info(String.format("Response Data is: %s", obj));
    }
}

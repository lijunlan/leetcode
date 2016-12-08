package com.sdll18.leetcode.spider.controller;

import com.sdll18.leetcode.spider.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-08
 */
@Controller
@RequestMapping(path = "/console", produces = "application/json;charset=UTF-8")
public class ConsoleController {

    @Autowired
    private CrawlerService crawlerService;


}

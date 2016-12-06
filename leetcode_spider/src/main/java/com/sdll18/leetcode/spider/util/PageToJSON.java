package com.sdll18.leetcode.spider.util;

import com.alibaba.fastjson.JSONObject;
import com.sdll18.leetcode.spider.model.page.Page;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-07
 */
public final class PageToJSON {

    public static JSONObject getJSON(Page page) {
        JSONObject r = new JSONObject();
        r.put("list", page.getRows());
        r.put("total", page.getTotal());
        return r;
    }
}

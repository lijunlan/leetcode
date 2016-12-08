import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdll18.leetcode.spider.constant.Code;
import com.sdll18.leetcode.spider.model.ProblemList;
import com.sdll18.leetcode.spider.service.ProblemListService;
import com.sdll18.leetcode.spider.service.ProblemService;
import com.sdll18.leetcode.spider.starter.Starter;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Starter.class)
public class MainTest {

    private static Logger logger = Logger.getLogger(MainTest.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProblemListService problemListService;

    @Autowired
    private ProblemService problemService;

    //    @Test
    public void listProblemList() {
        logger.info("list problem list started!");
        ResponseEntity<String> entity = restTemplate.getForEntity("https://leetcode.com/api/problems/algorithms/", String.class);
        String content = entity.getBody();
        JSONObject jsonObject = JSON.parseObject(content);
        JSONArray jsonArray = jsonObject.getJSONArray("stat_status_pairs");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            object = object.getJSONObject("stat");
            String name = object.getString("question__title");
            String path = "/problems/" + object.getString("question__title_slug");
            Integer qid = object.getInteger("question_id");
            ProblemList list = new ProblemList();
            list.setName(name);
            list.setNumber(qid);
            list.setPath(path);
            JSONObject result;
            do {
                result = problemListService.saveProblemList((JSONObject) JSON.toJSON(list));
            } while (result.getIntValue("code") == Code.ERROR_SAVE_FAILED);
        }
        logger.info("list problem list finished!");
    }

    @Test
    public void listProblem() {
        logger.info("list problem started!");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("start", 0);
        jsonObject.put("end", 500);
        JSONObject r = problemListService.listProblemList(jsonObject);
        if (r.getIntValue("code") == Code.SUCCESS) {
            JSONArray array = r.getJSONObject("data").getJSONArray("list");
            for (int i = 0; i < array.size(); i++) {
                try {
                    JSONObject object = array.getJSONObject(i);
                    ProblemList problemList = JSON.toJavaObject(object, ProblemList.class);
                    URL url = new URL("https://leetcode.com" + problemList.getPath());
                    Document doc = Jsoup.parse(url, 3000);
                    Element contentElement = doc.select("div.question-content").first();
                    String content = contentElement.html();
                    JSONObject inData = new JSONObject();
                    inData.put("number", problemList.getNumber());
                    inData.put("name", problemList.getName());
                    inData.put("content", content);
                    JSONObject result;
                    do {
                        result = problemService.saveProblem(inData);
                    } while (result.getIntValue("code") == Code.ERROR_SAVE_FAILED);
                    logger.info(result);
                } catch (Exception e) {
                    logger.error("failed to visit problem!", e);
                    logger.error(array.getJSONObject(i));
                }
            }
        }
        logger.info("list problem finished!");
    }


}

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdll18.leetcode.spider.constant.Code;
import com.sdll18.leetcode.spider.model.ProblemList;
import com.sdll18.leetcode.spider.service.ProblemListService;
import com.sdll18.leetcode.spider.starter.Starter;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

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

    @Test
    public void listProblemList() {
        System.out.println("started");
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
            logger.info(result);
            System.out.println(result);
        }
        logger.info("list problem list finished!");
        System.out.println("list problem list finished!");
    }
}

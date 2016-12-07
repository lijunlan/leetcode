import com.sdll18.leetcode.spider.starter.Starter;
import com.sdll18.leetcode.spider.task.ProblemListTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-12-07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Starter.class)
public class MainTest {

    @Test
    public void test() {
        ProblemListTask task = new ProblemListTask();
        task.listProblemList();
    }
}

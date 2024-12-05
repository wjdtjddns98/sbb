package com.mysite.sbb;

import com.mysite.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InputTest {

    @Autowired
    private QuestionService qService;

    @Test
    void test() {
        for (int i = 0; i < 300; i++) {
            String s = String.format("민정아사랑해 : [%03d]", i);
            String c = "내용없습니다";
            qService.createQuestion(s, c, null);
        }
    }
}

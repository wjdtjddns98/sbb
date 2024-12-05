package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AnswerTests {

    @Autowired
    private QuestionRepository qRepo;

    @Autowired
    private AnswerRepository aRepo;

    @Test
    public void input() {
        Optional<Question> oq = this.qRepo.findById(1);
        Question q = oq.get();
        Answer a = new Answer();
        a.setQuestion(q);
        a.setContent("asdf");
        a.setCreateDate(LocalDateTime.now());
        aRepo.save(a);
    }

    @Transactional
    @Test
    public void findAllbyQuestion() {
        Optional<Question> oq = this.qRepo.findById(1);
        Question q = oq.get();

        List<Answer> aList = q.getAnswerList();
        for(Answer a : aList) {
            System.out.println(a.getQuestion());
        }
    }

}

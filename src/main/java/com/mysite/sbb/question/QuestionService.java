package com.mysite.sbb.question;

import com.mysite.sbb.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository qRepo;
    //질문을 DB에서 가져오는 메서드(페이지 적용)
    public Page<Question> getList(int page){
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createDate").descending());
        return this.qRepo.findAll(pageable);
    }

    public Question getQuestion(int id) {
        Optional<Question> q = qRepo.findById(id);
        if (q.isPresent()) {
            return q.get();
        }else{
            throw new DataNotFoundException("qusetion not found");
        }
    }
    //새 질문을 저장하는 메서드
    public void createQuestion(String subject, String content, SiteUser author){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(author);
        qRepo.save(q);
    }
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        qRepo.save(question);
    }

    public void delete(Question question) {
        this.qRepo.delete(question);
    }


}

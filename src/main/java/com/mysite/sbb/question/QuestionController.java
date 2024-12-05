package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService qService;

    @Autowired
    private UserService uService;

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> paging = this.qService.getList(page);
        model.addAttribute("paging", paging);
        return "q_list";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model, AnswerForm answerForm) {
        // 서비스로 질문 내용을 가져옴
        Question question = this.qService.getQuestion(id);
        model.addAttribute("question", question);
        return "q_detail";
    }

    //질문 글쓰기는 인증되지 않으면 요청안되게(접근불가)
//    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(QuestionForm questionForm) {
        return "q_form";
    }

    //    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String qcreate(@Valid QuestionForm questionForm,
                          BindingResult bindingResult, Principal principal) {
        //@Valid로 해당 객체를 유효성검사
        SiteUser siteUser = (principal != null) ? uService.getUser(principal.getName()) : null;

        if (bindingResult.hasErrors()) {
            return "q_form";
        }

        qService.createQuestion(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";

    }

    //수정 : 인증된 유저
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") int id, QuestionForm questionForm, Principal principal, Model model) {
        //수정할 질문을 아이디로 꺼내온다
        Question question = this.qService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 접근입니다");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "q_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String qustionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal,
                                @PathVariable int id) {
        if (bindingResult.hasErrors()) {
            return "q_form"; //q_form으로 되돌아감
        }
        Question question = this.qService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
        }
        this.qService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/detail/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.qService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.qService.delete(question);
        return "redirect:/";
    }
}
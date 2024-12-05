package com.mysite.sbb.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

//질문 입력용 class
@Getter
@Setter
public class QuestionForm {
    @NotBlank(message = "제목은 필수 항목 입니다")
    @Size(max = 200, message = "제목은 200자 이하 입니다")
    private String subject;
    
    @NotBlank(message = "내용은 필수 항목 입니다")
    @Size(min=5, message = "내용을 5자 이상 입력해주세요")
    private String content;
}

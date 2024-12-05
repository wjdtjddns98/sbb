package com.mysite.sbb.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class AnswerForm {

    @NotBlank(message = "내용은 필수 입니다")
    @Size(min = 3, message = "최소 3자 이상 작성 해주세요")
    private String content;




}

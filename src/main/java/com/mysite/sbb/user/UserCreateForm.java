package com.mysite.sbb.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

//회원가입시 폼에 매핑하는 객체
@Getter
@Setter
public class UserCreateForm {

    @Size(min=3, max=25, message = "3~25글자까지 가능합니다")
    @NotEmpty(message = "아이디는 필수항목 입니다")
    private String username;
    @NotEmpty(message = "비밀번호는 필수항목 입니다")
    private String password1;
    @NotEmpty(message = "비밀번호 확인은 필수항목 입니다")
    private String password2;
    @NotEmpty(message = "이메일은 필수항목 입니다")
    @Email()
    private String email;
}

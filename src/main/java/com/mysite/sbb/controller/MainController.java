package com.mysite.sbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class MainController {

    @RequestMapping("/")
    public String root(){
        //리다이렉트는 새로운 요청(질문 컨트롤러)을 하는것
        return "redirect:/question/list";
    }
}

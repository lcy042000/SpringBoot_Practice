package com.example.springbootFirst.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/greetings")
    public String niceToMeetYou(Model model){
        model.addAttribute("username", "LEE");
        return "greetings"; // templates/greetings.mustache -> 브라우저로 전송
    }
}

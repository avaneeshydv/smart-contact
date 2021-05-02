package com.smart.contact.controller;

import com.smart.contact.config.ApplicationConstant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping("/signin")
    public String login(Model model) {
        model.addAttribute("title", "Login" + ApplicationConstant.APPLICATION_NAME);
        return "login";
    }
}
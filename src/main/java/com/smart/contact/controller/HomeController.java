package com.smart.contact.controller;

import javax.validation.Valid;

import com.smart.contact.config.ApplicationConstant;
import com.smart.contact.entity.User;
import com.smart.contact.service.UserService;
import com.smart.contact.vo.SignUpUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        log.info("Inside home page handler..");
        model.addAttribute("title", "Home" + ApplicationConstant.APPLICATION_NAME);

        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        log.info("Inside about page handler..");
        model.addAttribute("title", "About" + ApplicationConstant.APPLICATION_NAME);

        return "about";
    }

}
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
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        log.info("Inside home page handler..");
        model.addAttribute("title", "Home" + ApplicationConstant.APPLICATION_NAME);

        return "home";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        log.info("Inside signup page handler..");
        model.addAttribute("title", "Regiser" + ApplicationConstant.APPLICATION_NAME);
        model.addAttribute("loginData", new SignUpUser());
        return "signup";
    }

    // process form here
    @PostMapping("/process")
    public String validateForm(@Valid @ModelAttribute("loginData") SignUpUser loginData, BindingResult result,
            Model model) {
        model.addAttribute("title", "Regiser" + ApplicationConstant.APPLICATION_NAME);
        if (result.hasErrors()) {
            for (ObjectError element : result.getAllErrors()) {
                log.error(element.getObjectName() + " " + element.getDefaultMessage());
            }
            User createdUser = userService.createUser(loginData);
            log.info("Inside validation and process handler.. payload {}", createdUser.toString());
            return "signup";
        }
        return "landing";
    }

    @GetMapping("/about")
    public String about(Model model) {
        log.info("Inside about page handler..");
        model.addAttribute("title", "About" + ApplicationConstant.APPLICATION_NAME);

        return "about";
    }

}
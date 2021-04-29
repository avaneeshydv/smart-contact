package com.smart.contact.controller;


import com.smart.contact.entity.User;
import com.smart.contact.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplicationController {

    @Autowired
    private UserService userService;


    @GetMapping("/test")
    @ResponseBody
    public String test(){
        User user = new User();
        user.setName("Maria");
        user.setAbout("its about user user Maria");
        userService.createUser(user);
        return "test";
    }
    
}
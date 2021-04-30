package com.smart.contact.controller;

import javax.validation.Valid;

import com.smart.contact.config.ApplicationConstant;
import com.smart.contact.entity.User;
import com.smart.contact.service.UserService;
import com.smart.contact.vo.LoginUser;
import com.smart.contact.vo.SignUpUser;

import org.apache.commons.lang3.StringUtils;
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
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        log.info("Inside login page handler..");
        model.addAttribute("title", "Login" + ApplicationConstant.APPLICATION_NAME);
        model.addAttribute("loginData", new LoginUser());
        return "login";
    }

    // process form here
    @PostMapping("/login_do")
    public String validateLoginForm(@Valid @ModelAttribute("loginData") LoginUser loginData, BindingResult result,
            Model model) {

        model.addAttribute("title", "Login" + ApplicationConstant.APPLICATION_NAME);

        User user = userService.getUser(loginData.getEmail());

        if (result.hasErrors()) {

            for (ObjectError element : result.getAllErrors()) {
                log.error(element.getObjectName() + " " + element.getDefaultMessage());
            }
            return "login";

        } else if (user == null) {

            log.error("Invalid Email!!");
            model.addAttribute("error", "invalidEmail");
            return "login";

        } else if (!StringUtils.equals(user.getPassword(), loginData.getPassword())) {

            // make passowrd null for validation
            log.info("invalid passoword...");
            model.addAttribute("error", "invalidPassword");
            return "login";

        } else {

            model.addAttribute("loggedinUser", user);
            log.info("Inside validation and process for login handler.. payload {}", user.toString());
            return "landing";
        }
    }

}
package com.smart.contact.controller;

import java.security.Principal;
import java.util.List;

import com.smart.contact.config.ApplicationConstant;
import com.smart.contact.entity.Contact;
import com.smart.contact.entity.User;
import com.smart.contact.service.ContactService;
import com.smart.contact.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;

    @GetMapping(value = "/profile")
    public String goToDashBoard(Model model, Principal userProncipal) {

        User user = (User) model.getAttribute("user");

        model.addAttribute("user", user);
        model.addAttribute("title", user.getFirstName() + ApplicationConstant.APPLICATION_NAME);

        return "/user/profile";
    }

    @GetMapping(value = "/index")
    public String getProfile(Model model, Principal userProncipal) {

        model.addAttribute("title", "User" + ApplicationConstant.APPLICATION_NAME);

        return "/user/dashboard";
    }

    @GetMapping("/send-mail")
    public String mailHandler(Model model) {
        model.addAttribute("title", "Mail" + ApplicationConstant.APPLICATION_NAME);
        // get all the contacts
        User user = (User) model.getAttribute("user");
        List<Contact> contacts = contactService.getAllContactForUser(user.getUserId());
        model.addAttribute("contacts", contacts);
        return "/user/send_mail";
    }

    @PostMapping("/do-send-mail")
    public String processSendMail(){
        return "user/dashboard";
    }

    /**
     * Add common data into response
     */
    @ModelAttribute
    private void addCommonData(Model model, Principal userProncipal) {
        String name = userProncipal.getName();
        log.info("Inside login page handler for {} ", name);

        User user = userService.getUser(name);

        model.addAttribute("user", user);
    }

}
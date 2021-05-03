package com.smart.contact.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.smart.contact.config.ApplicationConstant;
import com.smart.contact.entity.Contact;
import com.smart.contact.entity.User;
import com.smart.contact.service.ContactService;
import com.smart.contact.service.UserService;
import com.smart.contact.vo.ModelResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

    @GetMapping(value = "/index")
    public String goToDashBoard(Model model, Principal userProncipal) {

        model.addAttribute("title", "User" + ApplicationConstant.APPLICATION_NAME);

        return "/user/dashboard";
    }

    @GetMapping("/add-contact")
    public String openAddContact(Model model) {
        model.addAttribute("title", "Add Contact" + ApplicationConstant.APPLICATION_NAME);
        model.addAttribute("contact", new Contact());
        return "user/add_contact";
    }

    @PostMapping("/do-add-contact")
    public String processContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult result, Model model,
            HttpSession session) {

        try {
            model.addAttribute("title", "Add Contact" + ApplicationConstant.APPLICATION_NAME);

        User user = (User) model.getAttribute("user");
        contact.setUser(user);

        log.info("Contct data to be addded: {}", contact);

        if (result.hasErrors()) {
            for (ObjectError element : result.getAllErrors()) {
                log.error(element.getObjectName() + " " + element.getDefaultMessage());
            }
        }

        // adding contact into database
        Contact cnt = contactService.addContact(contact);
        if (cnt == null) {
            log.error("Unable to add contact {}", contact.getName());
            session.setAttribute("message", new ModelResponse("failed to add contact!!", "alert-danger"));
        } else {
            log.error("Successfully added contact {}", contact.getName());
            session.setAttribute("message",
                    new ModelResponse("Successfully added contact for " + contact.getName(), "alert-success"));
        }
        } catch (Exception e) {
            log.error("Unable to add contact", e);
            session.setAttribute("message", new ModelResponse("failed to add contact!!", "alert-danger"));
        }

        return "user/add_contact";

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
package com.smart.contact.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.smart.contact.config.ApplicationConstant;
import com.smart.contact.entity.User;
import com.smart.contact.service.FileService;
import com.smart.contact.service.UserService;
import com.smart.contact.utils.FileUtils;
import com.smart.contact.vo.ModelResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SignupController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @GetMapping("/signup")
    public String signup(Model model) {
        log.info("Inside signup page handler..");
        model.addAttribute("title", "Regiser" + ApplicationConstant.APPLICATION_NAME);
        model.addAttribute("signupData", new SignUpUser());
        return "signup";
    }

    // process form here
    @PostMapping("/signup_do")
    public String validateForm(@Valid @ModelAttribute("signupData") SignUpUser signupData, BindingResult result,
            Model model, HttpSession session, @RequestParam("profileImage") MultipartFile file,
            @RequestParam(value = "agreement", defaultValue = "false") boolean agreement) {

        try {

            if (!agreement) {
                log.error("Agreement is not accepted!");
                throw new Exception("Please accept terms and conditions");
            }
            if (result.hasErrors()) {
                for (ObjectError element : result.getAllErrors()) {
                    log.error(element.getObjectName() + " " + element.getDefaultMessage());
                }
            } else {
                // upload image with photo name
                if (!file.isEmpty()) {

                    String errorMessage = FileUtils.validateFile(file);

                    if (StringUtils.isNotBlank(errorMessage)) {
                        throw new Exception(errorMessage);
                    }
                    fileService.uploadFile(file, file.getOriginalFilename());
                    // set name for image in contact
                    signupData.setImageUrl(file.getOriginalFilename());
                } else {
                    // set default name for image in contact
                    signupData.setImageUrl(ApplicationConstant.DEFAULT_IMG);
                }
                User createdUser = userService.createUser(signupData);
                log.info("Inside validation and process for signup handler.. payload {}", createdUser.toString());

                session.setAttribute("message",
                        new ModelResponse("Welcome " + createdUser.getFirstName(), "alert-success"));

                model.addAttribute("signupData", new SignUpUser());
            }

        } catch (Exception e) {
            log.error("Error occurred", "Something went wrong!!" + e.getMessage());
            session.setAttribute("message", new ModelResponse(
                    "Something went wrong!! " + e.getMessage() == null ? "" : e.getMessage(), "alert-danger"));
        }
        return "signup";
    }

}
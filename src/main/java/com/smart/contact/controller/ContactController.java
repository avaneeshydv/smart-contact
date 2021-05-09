package com.smart.contact.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.smart.contact.config.ApplicationConstant;
import com.smart.contact.entity.Contact;
import com.smart.contact.entity.User;
import com.smart.contact.service.ContactService;
import com.smart.contact.service.FileService;
import com.smart.contact.service.UserService;
import com.smart.contact.utils.FileUtils;
import com.smart.contact.vo.ModelResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/user")
public class ContactController {

    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private FileService fileService;

    @GetMapping("/add-contact")
    public String openAddContact(Model model) {
        model.addAttribute("title", "Add Contact" + ApplicationConstant.APPLICATION_NAME);
        model.addAttribute("contact", new Contact());
        // add header
        model.addAttribute("header", "Add Contact");
        // add post url action
        model.addAttribute("action", "/user/do-add-contact");

        return "user/add_update_contact";
    }

    @PostMapping("/do-add-contact")
    public String processContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult result, Model model,
            HttpSession session, @RequestParam("profileImage") MultipartFile file) {

        model.addAttribute("title", "Add Contact" + ApplicationConstant.APPLICATION_NAME);
        User user = (User) model.getAttribute("user");
        contact.setUser(user);

        try {

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
                    contact.setImageUrl(file.getOriginalFilename());
                }else{
                    // set default name for image in contact
                    contact.setImageUrl("image.png");
                }

                // adding contact into database
                Contact cnt = contactService.addContact(contact);
                if (cnt == null) {
                    log.error("Unable to add contact {}", contact.getName());
                    session.setAttribute("message", new ModelResponse("failed to add contact!!", "alert-danger"));
                } else {
                    log.error("Successfully added contact {}", contact.getName());
                    session.setAttribute("message",
                            new ModelResponse("Successfully added contact " + contact.getName(), "alert-success"));
                    model.addAttribute("contact", new Contact());
                }
            }
        } catch (Exception e) {
            log.error("Unable to add contact", e);
            session.setAttribute("message",
                    new ModelResponse("failed to add contact!! " + e.getMessage(), "alert-danger"));
        }

        return "user/add_update_contact";

    }

    // number of page
    // per page 5[n]
    // current page 0[page]
    @GetMapping("/all-contact/{page}")
    public String getAllContact(Model model, @PathVariable("page") int currentpage) {
        model.addAttribute("title", "Get Contact" + ApplicationConstant.APPLICATION_NAME);

        User user = (User) model.getAttribute("user");

        populAllContactWithPaginationForUser(user, model, currentpage);

        return "user/all_contact";
    }

    @GetMapping("/delete")
    public String deleteContact(Model model, @RequestParam String contactId, HttpSession session) {

        try {
            User user = (User) model.getAttribute("user");
            log.info("contac id------{}", contactId);
            if (user == null) {
                throw new Exception("Something went wrong! Please login and retry!");
            }
            // delete contact
            contactService.deleteContact(Integer.valueOf(contactId));
            // get all contact into our model
            populAllContactWithPaginationForUser(user, model, 0);

            session.setAttribute("message", new ModelResponse("Successfully removed Contact!! ", "alert-success"));
        } catch (Exception e) {
            log.error(e.getMessage());
            session.setAttribute("message",
                    new ModelResponse("failed to delete contact!! " + e.getMessage(), "alert-danger"));
        }
        return "user/all_contact";
    }

    @GetMapping("/update")
    public String handleUpdate(Model model, @RequestParam String contactId, HttpSession session) {

        model.addAttribute("title", "Update Contact" + ApplicationConstant.APPLICATION_NAME);
        try {
            User user = (User) model.getAttribute("user");
            log.info("contac id------{}", contactId);
            if (user == null) {
                throw new Exception("Something went wrong! Please login and retry!");
            }
            Contact contact = contactService.getContactByID(Integer.valueOf(contactId));
            model.addAttribute("contact", contact);
            // add header
            model.addAttribute("header", "Update Contact");
            // add post url action
            model.addAttribute("action", "/user/do-update?contactId=" + contactId);

        } catch (Exception e) {
            log.error(e.getMessage());
            session.setAttribute("message",
                    new ModelResponse("failed to update contact!! " + e.getMessage(), "alert-danger"));
        }
        return "user/add_update_contact";
    }

    @PostMapping("/do-update")
    public String updateContact(Model model, @ModelAttribute("contact") Contact contact, @RequestParam String contactId,
            HttpSession session) {

        try {
            User user = (User) model.getAttribute("user");
            if (user == null) {
                throw new Exception("Something went wrong! Please login and retry!");
            }
            // set contact id coming in request param
            contact.setContactId(Integer.parseInt(contactId));
            // set user
            contact.setUser(user);
            // update
            Contact updatedContact = contactService.updateContact(contact);

            session.setAttribute("message",
                    new ModelResponse("Successfully updated details for " + updatedContact.getName(), "alert-success"));
            // populate updated user
            populAllContactWithPaginationForUser(user, model, 0);
        } catch (Exception e) {
            log.error(e.getMessage());
            session.setAttribute("message",
                    new ModelResponse("failed to update contact!! " + e.getMessage(), "alert-danger"));
        }
        return "user/all_contact";
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

    private void populAllContactWithPaginationForUser(User user, Model model, int currentpage) {
        Page<Contact> contacts = contactService.getAllContactForUser(user.getUserId(), currentpage, 5);

        model.addAttribute("contacts", contacts);
        model.addAttribute("currentPage", currentpage);
        model.addAttribute("totalPages", contacts.getTotalPages());

    }

}
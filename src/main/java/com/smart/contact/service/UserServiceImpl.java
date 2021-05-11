package com.smart.contact.service;

import java.time.LocalDate;

import com.smart.contact.config.ApplicationConstant;
import com.smart.contact.dao.UserRepository;
import com.smart.contact.entity.User;
import com.smart.contact.vo.SignUpUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User createUser(SignUpUser user) {

        log.info("Creating user with details {}", user);

        User userToBeAdded = new User();
        userToBeAdded.setFirstName(user.getFirstName());
        userToBeAdded.setLastName(user.getLastName());
        userToBeAdded.setGender(user.getGender().charAt(0));
        userToBeAdded.setEmail(StringUtils.lowerCase(user.getEmail()));
        userToBeAdded.setPassword(passwordEncoder.encode(user.getPassword()));
        userToBeAdded.setEnabled(true);
        userToBeAdded.setImageUrl(user.getImageUrl());
        userToBeAdded.setAbout(user.getDescription());
        if (StringUtils.isNotBlank(user.getDateOfBirth())) {
            userToBeAdded.setDateOfBirth(LocalDate.parse(user.getDateOfBirth()));
        }
        userToBeAdded.setRole(ApplicationConstant.ROLE_USER);

        return repository.save(userToBeAdded);
    }

    @Override
    public User getUser(String email) {
        return repository.findByEmail(email);
    }

}
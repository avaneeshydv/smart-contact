package com.smart.contact.service;

import com.smart.contact.entity.User;
import com.smart.contact.vo.SignUpUser;

public interface UserService {

    User createUser(SignUpUser user);
    
}
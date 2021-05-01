package com.smart.contact.config;

import com.smart.contact.dao.UserRepository;
import com.smart.contact.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usr = userRepository.findByEmail(username);
        if(usr == null){
            throw new UsernameNotFoundException("No user found for this email!");
        }
        return new CustomUserDetails(usr);
    }

    
}
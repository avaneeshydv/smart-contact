package com.smart.contact.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginUser {

    private static final String PASS_REG = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";

    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email Address!")
    private String email;
    @Pattern(regexp = PASS_REG, message = "Invalid password!")
    @Size(min = 8, max = 20, message = "password must be 8 to 20 characters long!")
    private String password;
}
package com.smart.contact.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignUpUser {

    private static final String PASS_REG = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";

    @NotBlank(message = "cannot have blank first name!")
    private String firstName;
    @NotBlank(message = "cannot have blank last name!")
    private String lastName;
    @NotBlank(message = "Please select gender!")
    @Size(max = 1, message = "Cannot have more Gender of length more that 1!")
    private String gender;
    @NotBlank(message = "Cannot have bank date of birth!")
    private String dateOfBirth;
    @NotBlank(message = "Cannot have blank email!")
    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email Address!")
    private String email;
    @Pattern(regexp = PASS_REG, message = "Invalid password!")
    @Size(min = 8, max = 20, message = "password must be 8 to 20 characters long!")
    private String password;
    private String description;
    private String imageUrl;

}
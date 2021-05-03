package com.smart.contact.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CONTACT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int contactId;
    @NotBlank(message = "Cannot have blank name!!")
    private String name;
    private String nickname;
    @NotBlank(message = "Cannot have blank email!!")
    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email Address!")
    private String email;
    @NotBlank(message = "Cannot have blank phone!!")
    @Size(min = 10, max = 13, message = "Phone no should be b/w 10 to 13 chaacters!")
    private String phone;
    private String work;
    private String imageUrl;
    private String address;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

}
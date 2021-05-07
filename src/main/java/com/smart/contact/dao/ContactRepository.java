package com.smart.contact.dao;

import java.util.List;

import com.smart.contact.entity.Contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query("from Contact as c where c.user.userId =:userId")
    List<Contact> getContactsByUserId(@Param("userId") int userId);

}
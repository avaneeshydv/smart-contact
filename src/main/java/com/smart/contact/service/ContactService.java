package com.smart.contact.service;

import java.util.List;

import com.smart.contact.entity.Contact;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ContactService {

    Contact addContact(Contact contact);

	Page<Contact> getAllContactForUser(int userId, int page, int size);

	void deleteContact(int contactId, int userId);

	Contact updateContact(Contact contact, MultipartFile file);

	Contact getContactByID(int valueOf);

	List<Contact> getAllContactForUser(int userId);

}
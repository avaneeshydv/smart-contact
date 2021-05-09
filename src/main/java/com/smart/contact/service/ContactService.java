package com.smart.contact.service;

import com.smart.contact.entity.Contact;

import org.springframework.data.domain.Page;

public interface ContactService {

    Contact addContact(Contact contact);

	Page<Contact> getAllContactForUser(int userId, int page, int size);

	void deleteContact(int contactId);

	Contact updateContact(Contact contact);

	Contact getContactByID(int valueOf);

}
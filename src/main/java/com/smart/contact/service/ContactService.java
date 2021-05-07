package com.smart.contact.service;

import java.util.List;

import com.smart.contact.entity.Contact;

public interface ContactService {

    Contact addContact(Contact contact);

	List<Contact> getAllContactForUser(int userId);

	void deleteContact(int contactId);

	Contact updateContact(Contact contact);

}
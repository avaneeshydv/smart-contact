package com.smart.contact.service;

import java.util.List;

import com.smart.contact.dao.ContactRepository;
import com.smart.contact.entity.Contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact addContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAllContactForUser(int userId) {

        return contactRepository.getContactsByUserId(userId);

    }

    @Override
    public void deleteContact(int contactId) {
        contactRepository.deleteById(contactId);
    }

    @Override
    public Contact updateContact(Contact contact) {
        Contact oldContact = contactRepository.getOne(contact.getContactId());
        
        oldContact = contact;

        return contactRepository.save(oldContact);
    }

}
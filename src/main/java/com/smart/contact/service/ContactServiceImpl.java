package com.smart.contact.service;

import javax.transaction.Transactional;

import com.smart.contact.dao.ContactRepository;
import com.smart.contact.entity.Contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Contact> getAllContactForUser(int userId, int page, int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        return contactRepository.getContactsByUserId(userId, pageRequest);

    }

    @Override
    public void deleteContact(int contactId) {
        contactRepository.deleteById(contactId);
    }

    @Override
    @Transactional
    public Contact updateContact(Contact contact) {
        // delete old contact
        contactRepository.deleteById(contact.getContactId());
        // insert new contact
        return contactRepository.save(contact);
    }

    @Override
    public Contact getContactByID(int contactId) {

        return contactRepository.findById(contactId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id: " + contactId));
    }

}
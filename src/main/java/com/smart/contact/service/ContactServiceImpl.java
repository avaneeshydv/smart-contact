package com.smart.contact.service;

import java.util.List;

import com.smart.contact.dao.ContactRepository;
import com.smart.contact.entity.Contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public void deleteContact(int contactId, int userId) {
        Contact contact = contactRepository.getOne(contactId);
        if (contact.getUser().getUserId() == userId) {
            contactRepository.deleteById(contactId);
        } else {
            throw new IllegalArgumentException("Unauthorized to delete the contact!");
        }
    }

    @Override
    public Contact updateContact(Contact contact, MultipartFile file) {
        // get the exiting image if there is no update for image
        // set user image
        String image = contactRepository.getOne(contact.getContactId()).getImageUrl();
        if (file.isEmpty()) {
            contact.setImageUrl(image);
        } else {
            contact.setImageUrl(file.getOriginalFilename());
        }

        // insert new contact
        return contactRepository.save(contact);
    }

    @Override
    public Contact getContactByID(int contactId) {

        return contactRepository.findById(contactId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id: " + contactId));
    }

    @Override
    public List<Contact> getAllContactForUser(int userId) {
        return contactRepository.getAllContactsForUser(userId);
    }

}
package com.oan.management.service;

import com.oan.management.model.Contact;
import com.oan.management.model.User;

import java.util.List;

/**
 * Created by Oan on 25/01/2018.
 */
public interface ContactService {
    List<Contact> findByUser(User user);
    Contact save(Contact contact);
    void deleteById(Long id);
    Contact getOne(Long id);
}

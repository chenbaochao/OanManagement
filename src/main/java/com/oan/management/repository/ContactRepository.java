package com.oan.management.repository;

import com.oan.management.model.Contact;
import com.oan.management.model.Task;
import com.oan.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Oan on 18/01/2018.
 */

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{
    List<Contact> findByUser(User user);
}

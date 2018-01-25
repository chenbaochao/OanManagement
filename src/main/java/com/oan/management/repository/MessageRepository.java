package com.oan.management.repository;

import com.oan.management.model.Message;
import com.oan.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Oan on 25/01/2018.
 */

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> getAllByReceiver(User user);
    Message getMessageById(Long id);
}

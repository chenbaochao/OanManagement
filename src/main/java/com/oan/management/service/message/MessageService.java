package com.oan.management.service.message;

import com.oan.management.model.Message;
import com.oan.management.model.User;

import java.util.List;

/**
 * Created by Oan on 25/01/2018.
 */
public interface MessageService {
    List<Message> getMessagesByUser(User user);
    Message getMessageById(Long id);
    void deleteMessageById(Long id);
    Message save(Message message);
    List<Message> getAllByReceiverAndOpenedIsFalse(User user);
    List<Message> findByReceiverAndOpenedIs(User user, int read);
}

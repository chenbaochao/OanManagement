package com.oan.management.service;

import com.oan.management.model.Message;
import com.oan.management.model.User;
import com.oan.management.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Oan on 25/01/2018.
 */

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;


    @Override
    public List<Message> getMessagesByUser(User user) {
        return messageRepository.getAllByReceiver(user);
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepository.getMessageById(id);
    }
}

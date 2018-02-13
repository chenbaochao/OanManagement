package com.oan.management.service.message;

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

    @Override
    public void deleteMessageById(Long id) {
        messageRepository.delete(messageRepository.getMessageById(id));
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getAllByReceiverAndOpenedIsFalse(User user) {
        return messageRepository.getAllByReceiverAndOpenedIsFalse(user);
    }

    @Override
    public List<Message> findByReceiverAndOpenedIs(User user, int read) {
        return messageRepository.findByReceiverAndOpenedIs(user, read);
    }


}

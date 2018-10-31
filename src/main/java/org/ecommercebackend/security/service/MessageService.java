package org.ecommercebackend.security.service;

import org.ecommercebackend.model.Message;
import org.ecommercebackend.model.Thread;
import org.ecommercebackend.security.repository.MessageRepository;
import org.ecommercebackend.security.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public Message addMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByThread(Thread thread){
        return messageRepository.findAllByThreadOrderByDatesentAsc(thread);
    }
}

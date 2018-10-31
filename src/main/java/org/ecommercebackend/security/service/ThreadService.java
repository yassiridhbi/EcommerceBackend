package org.ecommercebackend.security.service;

import org.ecommercebackend.model.Customer;
import org.ecommercebackend.model.Message;
import org.ecommercebackend.model.Thread;
import org.ecommercebackend.security.repository.CustomerRepository;
import org.ecommercebackend.security.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadService {

    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Thread addThread(Thread thread){
        return threadRepository.save(thread);
    }

    public void threadSeen(Thread thread){
        thread.setAlreadyRead(true);
        threadRepository.save(thread);
    }

    public Thread findThreadbyName(String name){
        return threadRepository.findByTitle(name);
    }

    public Thread findThreadById(long id) { return threadRepository.findById(id); }

    public List<Thread> findThreadsByUser(Customer cst) {
        return threadRepository.findAllByUser1OrUser2OrderByLastSentMessageDesc(cst, cst);
    }

    public List<Thread> findUnreadThreadsByUser(Customer cst) {
        return threadRepository.findAllByUser2AndAlreadyReadIsFalse(cst);
    }
}

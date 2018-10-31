package org.ecommercebackend.security.controller;

import org.ecommercebackend.model.Customer;
import org.ecommercebackend.model.Message;
import org.ecommercebackend.model.Thread;
import org.ecommercebackend.security.JwtTokenUtil;
import org.ecommercebackend.security.service.CustomerService;
import org.ecommercebackend.security.service.MessageService;
import org.ecommercebackend.security.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:8081", "http://127.0.0.1:8081"  }, allowCredentials = "false")
public class MessagingController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    CustomerService customerService;

    @Autowired
    ThreadService threadService;

    @Autowired
    MessageService messageService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "api/contact/thread/{id}", method = RequestMethod.POST)
    public Message replyToThread(HttpServletRequest request, @PathVariable long id) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        Thread thread = threadService.findThreadById(id);
        Customer cst2 ;
        if(thread.getUser_1().equals(cst))
            cst2 = thread.getUser_2();
        else
            cst2 = thread.getUser_1();

        if(thread.getUser_1().equals(cst) || thread.getUser_2().equals(cst)){
            Message message = new Message();
            message.setDate_sent(new Date());
            message.setContent(request.getParameter("content"));
            message.setUser_1(cst);
            message.setUser_2(cst2);
            message.setThread(thread);
            thread.setUser_1(message.getUser_1());
            thread.setUser_2(message.getUser_2());
            threadService.addThread(thread);
            return messageService.addMessage(message);
        }
        else
            return null;
    }

    @RequestMapping(value = "api/contact/{id}", method = RequestMethod.POST)
    public Thread addNewThread(HttpServletRequest request, @PathVariable long id) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer sender = customerService.findCustomerByUserUsername(username);
        Customer destination = customerService.findCustomerByUserId(id);
        Thread thread = new Thread();
        thread.setAlreadyRead(false);
        thread.setTitle(request.getParameter("title"));
        thread.setUser_1(sender);
        thread.setUser_2(destination);
        thread.setLastSentMessage(new Date());
        Thread savedThread =  threadService.addThread(thread);
        Message message = new Message();
        message.setThread(savedThread);
        message.setDate_sent(new Date());
        message.setUser_1(sender);
        message.setUser_2(destination);
        message.setContent(request.getParameter("content"));
        messageService.addMessage(message);
        return savedThread;
    }

    @RequestMapping(value = "api/contact/thread/{id}", method = RequestMethod.GET)
    public List<Message> getThread(HttpServletRequest request, @PathVariable long id) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        Thread thread = threadService.findThreadById(id);

        if(thread.getUser_1().equals(cst) || thread.getUser_2().equals(cst)){
            if(thread.getUser_2().equals(cst)){
                thread.setAlreadyRead(true);
                threadService.addThread(thread);
            }
            return messageService.getMessagesByThread(thread);
        }

        else
            return null;
    }

    @RequestMapping(value = "api/contact/threads/", method = RequestMethod.GET)
    public List<Thread> getThreads(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        return threadService.findThreadsByUser(cst);
    }

    @RequestMapping(value = "api/contact/threads/unread/", method = RequestMethod.GET)
    public List<Thread> getUnreadThreads(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        return threadService.findUnreadThreadsByUser(cst);
    }
}

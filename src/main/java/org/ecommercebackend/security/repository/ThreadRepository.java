package org.ecommercebackend.security.repository;

import org.ecommercebackend.model.Customer;
import org.ecommercebackend.model.Message;
import org.ecommercebackend.model.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThreadRepository extends JpaRepository<Thread, Long> {

    public Thread findByTitle(String title);

    public Thread findById(long id);

    public List<Thread> findAllByUser1OrUser2OrderByLastSentMessageDesc(Customer customer, Customer customer2);

    public List<Thread> findAllByUser2AndAlreadyReadIsFalse(Customer customer);

}

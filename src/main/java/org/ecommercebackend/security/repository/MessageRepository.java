package org.ecommercebackend.security.repository;

import org.ecommercebackend.model.Message;
import org.ecommercebackend.model.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByThreadOrderByDatesentAsc(Thread thread);

}

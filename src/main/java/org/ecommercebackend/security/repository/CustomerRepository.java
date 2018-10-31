package org.ecommercebackend.security.repository;

import org.ecommercebackend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByUserId(Long id);

    Customer findByUserUsername(String username);

}

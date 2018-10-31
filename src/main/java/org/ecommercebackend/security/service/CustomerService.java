package org.ecommercebackend.security.service;


import org.ecommercebackend.model.Customer;
import org.ecommercebackend.security.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer findCustomerByUserId(Long id){
        return customerRepository.findByUserId(id);
    }

    public Customer findCustomerByUserUsername(String username){
        return customerRepository.findByUserUsername(username);
    }

    public void saveCustomer(Customer customer){
        customerRepository.save(customer);
    }

}

package org.ecommercebackend.security.repository;


import org.ecommercebackend.model.Customer;
import org.ecommercebackend.model.CustomerProduct;
import org.ecommercebackend.model.CustomerProductPK;
import org.ecommercebackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerProductRepository extends JpaRepository<CustomerProduct, CustomerProductPK> {

    List<CustomerProduct> findAllByCustomer(Customer customer);

    List<CustomerProduct> findAllByProduct(Product product);

}

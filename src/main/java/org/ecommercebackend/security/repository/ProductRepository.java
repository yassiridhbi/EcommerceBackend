package org.ecommercebackend.security.repository;

import org.ecommercebackend.model.Customer;
import org.ecommercebackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCustomerId(Long id);

    Product findProductById(long id);

    List<Product> findTop10ByOrderByDateAjoutDesc();

}

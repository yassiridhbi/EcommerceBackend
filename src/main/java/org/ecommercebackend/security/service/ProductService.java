package org.ecommercebackend.security.service;

import org.ecommercebackend.model.Customer;
import org.ecommercebackend.model.Product;
import org.ecommercebackend.security.repository.CustomerRepository;
import org.ecommercebackend.security.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    public List<Product> findProductByCustmerId(Long id){
        return productRepository.findByCustomerId(id);
    }


    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product findProductById(long id) {
        return productRepository.findProductById(id);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findNewProducts() {
        return productRepository.findTop10ByOrderByDateAjoutDesc();
    }
}

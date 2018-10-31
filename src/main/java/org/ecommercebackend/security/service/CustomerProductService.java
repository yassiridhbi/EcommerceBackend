package org.ecommercebackend.security.service;

import org.ecommercebackend.model.Customer;
import org.ecommercebackend.model.CustomerProduct;
import org.ecommercebackend.model.CustomerProductPK;
import org.ecommercebackend.model.Product;
import org.ecommercebackend.security.repository.CustomerProductRepository;
import org.ecommercebackend.security.repository.CustomerRepository;
import org.ecommercebackend.security.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class CustomerProductService {

    @Autowired
    CustomerProductRepository customerProductRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public CustomerProduct addCustomerProduct(Customer customer, Product product, String address) {
        CustomerProduct customerProduct = new CustomerProduct();
        customerProduct.setDateAjout(new Date());
        customerProduct.setCustomer(customer);
        customerProduct.setProduct(product);
        customerProduct.setShippingAddress(address);
        return customerProductRepository.save(customerProduct);
    }

    public List<CustomerProduct> findCustomerProductByCustomer(Customer customer){
        return customerProductRepository.findAllByCustomer(customer);
    }

    public List<CustomerProduct> findCustomerProductByProduct(Product product){
        return customerProductRepository.findAllByProduct(product);
    }


    public CustomerProduct findCustomerProductByCustomerAndProduct(Customer cst, Product product) {
        CustomerProductPK pk = new CustomerProductPK();
        pk.setCustomer_id(cst.getId());
        pk.setProduct_id(product.getId());
        return customerProductRepository.findOne(pk);
    }
}

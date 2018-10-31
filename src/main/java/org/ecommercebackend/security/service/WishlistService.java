package org.ecommercebackend.security.service;

import org.ecommercebackend.model.*;
import org.ecommercebackend.security.repository.CustomerProductRepository;
import org.ecommercebackend.security.repository.CustomerRepository;
import org.ecommercebackend.security.repository.ProductRepository;
import org.ecommercebackend.security.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class WishlistService {

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public Wishlist addProductToWishlist(Customer customer, Product product) {
        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(customer);
        wishlist.setProduct(product);
        wishlist.setDateAjout(new Date());
        return wishlistRepository.save(wishlist);
    }

    public List<Wishlist> findProductsInWishlistByCustomer(Customer customer){
        return wishlistRepository.findAllByCustomer(customer);
    }

    public Wishlist findProductInWishlistByCustomer(Customer customer, long product_id){
        WishlistPK wishlist = new WishlistPK();
        wishlist.setProduct_id(product_id);
        wishlist.setCustomer_id(customer.getId());
        return wishlistRepository.findOne(wishlist);
    }

    public void deleteWishlist(Wishlist wishlist) {
        wishlistRepository.delete(wishlist);
    }
}


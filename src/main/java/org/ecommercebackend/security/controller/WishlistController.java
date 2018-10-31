package org.ecommercebackend.security.controller;


import org.ecommercebackend.model.Customer;
import org.ecommercebackend.model.CustomerProduct;
import org.ecommercebackend.model.Product;
import org.ecommercebackend.model.Wishlist;
import org.ecommercebackend.security.JwtTokenUtil;
import org.ecommercebackend.security.repository.WishlistRepository;
import org.ecommercebackend.security.service.CustomerProductService;
import org.ecommercebackend.security.service.CustomerService;
import org.ecommercebackend.security.service.ProductService;
import org.ecommercebackend.security.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:8081", "http://127.0.0.1:8081"  }, allowCredentials = "false")
public class WishlistController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    CustomerService customerService;

    @Autowired
    WishlistService wishlistService;

    @Autowired
    ProductService productService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @RequestMapping(value = "api/wishlist/add/", method = RequestMethod.POST)
    public Wishlist addToWishlist(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        Product product = productService.findProductById(Integer.parseInt(request.getParameter("product_id")));
        if(!(product.getCustomer().equals(cst))){
            return wishlistService.addProductToWishlist(cst,product);

        }
        else
            return null;
    }

    @RequestMapping(value = "api/wishlist/remove/", method = RequestMethod.POST)
    public String removeFromWishlist(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        Product product = productService.findProductById(Integer.parseInt(request.getParameter("product_id")));
        Wishlist wishlist = wishlistService.findProductInWishlistByCustomer(cst, product.getId());
        if(wishlist != null){
            wishlistService.deleteWishlist(wishlist);
            return "Success";
        }
        else
            return null;
    }



    @RequestMapping(value = "api/wishlist/", method = RequestMethod.GET)
    public List<Wishlist> getWishlistProduct(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        return wishlistService.findProductsInWishlistByCustomer(cst);
    }

    @RequestMapping(value = "api/wishlist/{id}", method = RequestMethod.GET)
    public Wishlist getWishlistProduct(HttpServletRequest request, @PathVariable long id) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        return wishlistService.findProductInWishlistByCustomer(cst, id);
    }

}

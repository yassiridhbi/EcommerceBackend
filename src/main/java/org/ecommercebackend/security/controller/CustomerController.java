package org.ecommercebackend.security.controller;

import org.ecommercebackend.model.Customer;
import org.ecommercebackend.model.Product;
import org.ecommercebackend.security.JwtTokenUtil;
import org.ecommercebackend.security.service.CustomerService;
import org.ecommercebackend.security.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:8081", "http://127.0.0.1:8081"  }, allowCredentials = "false")
public class CustomerController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "api/customer/{id}", method = RequestMethod.GET)
    public Customer getCustomerInfo(HttpServletRequest request, @PathVariable long id) {
        Customer cst =customerService.findCustomerByUserId(id);
        cst.setUser(null);
        return cst;


    }

    @RequestMapping(value = "api/customer/", method = RequestMethod.POST)
    public String postCustomerInfo(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        cst.setAdresse(request.getParameter("adresse"));
        cst.setFirstname(request.getParameter("firstname"));
        cst.setLastname(request.getParameter("lastname"));
        cst.setEmail(request.getParameter("email"));
        customerService.saveCustomer(cst);
        return "Saved";
    }

    @RequestMapping(value = "api/customer/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Product> getCustomerProducts(HttpServletRequest request, @PathVariable long id) {
        List<Product> list = productService.findProductByCustmerId(id);
        Customer cst =customerService.findCustomerByUserId(id);
        return list;
    }
}

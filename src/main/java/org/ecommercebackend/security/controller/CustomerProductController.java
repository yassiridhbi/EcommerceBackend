package org.ecommercebackend.security.controller;


import org.ecommercebackend.model.Customer;
import org.ecommercebackend.model.CustomerProduct;
import org.ecommercebackend.model.Product;
import org.ecommercebackend.security.JwtTokenUtil;
import org.ecommercebackend.security.service.CustomerProductService;
import org.ecommercebackend.security.service.CustomerService;
import org.ecommercebackend.security.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:8081", "http://127.0.0.1:8081"  }, allowCredentials = "false")
public class CustomerProductController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerProductService customerProductService;

    @Autowired
    ProductService productService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "api/orders/", method = RequestMethod.POST)
    public String postCustomerInfo(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        Product product = productService.findProductById(Integer.parseInt(request.getParameter("product_id")));
        String shippingAddress = request.getParameter("shipping_address ");
        if(!(product.getCustomer().equals(cst))){
            customerProductService.addCustomerProduct(cst, product, shippingAddress);
            return "Saved";
        }
        else
            return null;
    }

        @RequestMapping(value = "api/orders/customer/", method = RequestMethod.GET)
    public List<CustomerProduct> ordersByCustomer(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        return customerProductService.findCustomerProductByCustomer(cst);
    }

    @RequestMapping(value = "api/orders/product/{id}", method = RequestMethod.GET)
    public List<CustomerProduct> ordersByProduct(HttpServletRequest request,  @PathVariable long id) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        System.out.println(username);
        Product product = productService.findProductById(id);
        System.out.println(product.getName());

        if(product.getCustomer().equals(cst)){
            List<CustomerProduct> customerProductByProduct = customerProductService.findCustomerProductByProduct(product);
            for (int i = 0;i<customerProductByProduct.size();i++){
                System.out.println(customerProductByProduct.get(i).getCustomer().getFirstname());
            }
            return customerProductByProduct;
        }
        else
            return null;
    }

    @RequestMapping(value = "api/orders/customer/{id}", method = RequestMethod.GET)
    public CustomerProduct ordersByProductandCustomer(HttpServletRequest request,  @PathVariable long id) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        Product product = productService.findProductById(id);
        if(!product.getCustomer().equals(cst)){

            return customerProductService.findCustomerProductByCustomerAndProduct(cst, product);
        }
        else
            return null;
    }

}

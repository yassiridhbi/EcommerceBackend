package org.ecommercebackend.security.controller;

import org.ecommercebackend.dto.FileInfo;
import org.ecommercebackend.model.Customer;
import org.ecommercebackend.model.Product;
import org.ecommercebackend.security.JwtTokenUtil;
import org.ecommercebackend.security.service.CustomerService;
import org.ecommercebackend.security.service.ProductService;
import org.ecommercebackend.security.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    private StorageService storageService;


    @Autowired
    ServletContext context;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @RequestMapping(value = "api/products/add", method = RequestMethod.POST)
    public ResponseEntity<FileInfo> addProduct(@RequestParam("file") MultipartFile inputFile, HttpServletRequest request) {
        if(request.getParameter("name") == null || (!inputFile.isEmpty() && !inputFile.getContentType().startsWith("image")))
            return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        Product product = new Product();
        FileInfo fileInfo = new FileInfo();
        HttpHeaders headers = new HttpHeaders();
        if (!inputFile.isEmpty()) {
            try {
                String oriFilename = inputFile.getOriginalFilename();
                System.out.println(inputFile.getContentType());

                String fileName = username + '_' + (int) Math.floor(Math.random()*1000000) + "." + oriFilename.substring(oriFilename.lastIndexOf('.')+1);
                File destinationFile = new File(context.getRealPath("/WEB-INF/uploaded")+  File.separator + fileName);
                inputFile.transferTo(destinationFile);
                fileInfo.setFileName(destinationFile.getPath());
                fileInfo.setFileSize(inputFile.getSize());
                product.setImgPath(fileName);
                headers.add("File Uploaded Successfully - ", oriFilename);
            } catch (Exception e) {
                return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
            }
        }
        product.setAuthor(request.getParameter("author"));
        product.setDateAjout(new Date());
        product.setCustomer(cst);
        product.setDescription(request.getParameter("description"));
        product.setLanguage(request.getParameter("language"));
        product.setName(request.getParameter("name"));
        product.setPublisher(request.getParameter("publisher"));
        product.setPrice(request.getParameter("price"));
        fileInfo.setProduct(product);
        productService.addProduct(product);
        return new ResponseEntity<FileInfo>(fileInfo, headers, HttpStatus.OK);
    }


    @RequestMapping(value = "api/products/modifyimage/{id}", method = RequestMethod.POST)
    public ResponseEntity<FileInfo> modifyImage(@RequestParam("file") MultipartFile inputFile, HttpServletRequest request, @PathVariable long id) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        Product product = productService.findProductById(id);
        FileInfo fileInfo = new FileInfo();
        HttpHeaders headers = new HttpHeaders();
        if(cst.equals(product.getCustomer())){
            if (!inputFile.isEmpty()) {
                try {
                    String fileName = product.getImgPath();
                    File destinationFile = new File(context.getRealPath("/WEB-INF/uploaded")+  File.separator + fileName);
                    inputFile.transferTo(destinationFile);
                    fileInfo.setFileName(destinationFile.getPath());
                    fileInfo.setFileSize(inputFile.getSize());
                    product.setImgPath(fileName);
                    headers.add("File Uploaded Successfully - ", fileName);
                } catch (Exception e) {
                    return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
                }

                return new ResponseEntity<FileInfo>(fileInfo, headers, HttpStatus.OK);
            }
            else
                return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
        }
        else
            return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @RequestMapping(value = "api/products/modify/{id}", method = RequestMethod.POST)
    public Product modifyProduct(HttpServletRequest request, @PathVariable long id) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        Product product = productService.findProductById(id);
        if(cst.equals(product.getCustomer())){
            product.setAuthor(request.getParameter("author"));
            product.setDescription(request.getParameter("description"));
            product.setLanguage(request.getParameter("language"));
            product.setName(request.getParameter("name"));
            product.setPublisher(request.getParameter("publisher"));
            product.setPrice(request.getParameter("price"));
            return productService.addProduct(product);
        }
        else{
            return null;
        }

    }

    @RequestMapping(value = "api/products/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> deleteProduct(HttpServletRequest request, @PathVariable long id) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cst = customerService.findCustomerByUserUsername(username);
        Product product = productService.findProductById(id);
        if(cst.equals(product.getCustomer())){
            productService.deleteProduct(product);
            return new ResponseEntity<String>(HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "api/products/{id}", method = RequestMethod.GET)
    public Product addProduct(HttpServletRequest request, @PathVariable long id) {
        Product product = productService.findProductById(id);
        return product;
    }

    @RequestMapping(value = "api/products", method = RequestMethod.GET)
    public List<Product> findProducts(HttpServletRequest request) {
        List<Product> product = productService.findAllProducts();
        return product;
    }

    @RequestMapping(value = "api/products/new", method = RequestMethod.GET)
    public List<Product> findNewProducts(HttpServletRequest request) {
        List<Product> product = productService.findNewProducts();
        return product;
    }



}

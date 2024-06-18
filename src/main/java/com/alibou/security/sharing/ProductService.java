package com.alibou.security.sharing;

import com.alibou.security.user.User;
import com.alibou.security.user.UserService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;


    public Product getProductByUserId(Integer userId) {
        User user = userService.getByUserId(userId);
        return productRepository.findByUser(user).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }




    @Async
    public CompletableFuture<Product> createProductAsync(Integer userId, Product product) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userService.getByUserId(userId);
            product.setUser(user);
            return productRepository.save(product);
        });
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Product updateProduct(Integer id, Product productDetails) {
        Product product = getProductById(id);
        product.setUrl(productDetails.getUrl());
        product.setPrice(productDetails.getPrice());
        product.setProductDesc(productDetails.getProductDesc());
        product.setAddress(productDetails.getAddress());
        product.setNumber(productDetails.getNumber());
        return productRepository.save(product);
    }

    public void deleteProduct(Integer id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}

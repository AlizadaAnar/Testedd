package com.alibou.security.sharing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "https://newapphere-ffa9a547cef0.herokuapp.com/", allowCredentials = "true", allowedHeaders = {"Authorization", "Content-Type"})
public class ProductController {

    @Autowired
    private ProductService productService;

    //    @CrossOrigin(origins = "https://newapphere-ffa9a547cef0.herokuapp.com/")
    @CrossOrigin(origins = "http://localhost:5174")
    @GetMapping("/user/product")
    public ResponseEntity<Product> getProductForAuthenticatedUser() {
        Integer userId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product product = productService.getProductByUserId(userId);
        return ResponseEntity.ok(product);
    }


    @CrossOrigin(origins = "http://localhost:5174")
    @PostMapping("/user/product")
    public CompletableFuture<ResponseEntity<Product>> createProduct(@RequestBody Product product) {
        Integer userId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return productService.createProductAsync(userId, product)
                .thenApply(createdProduct -> ResponseEntity.status(HttpStatus.CREATED).body(createdProduct))
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                });
    }

    @CrossOrigin(origins = "http://localhost:5174")
    @GetMapping("/takeAll")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @CrossOrigin(origins = "http://localhost:5174")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @CrossOrigin(origins = "http://localhost:5174")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, productDetails));
    }

    @CrossOrigin(origins = "http://localhost:5174")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
package com.microserviceprojectdemo.ProductService.controller;


import com.microserviceprojectdemo.ProductService.model.ProductRequest;
import com.microserviceprojectdemo.ProductService.model.ProductResponse;
import com.microserviceprojectdemo.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired()
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {
        Long productId = productService.addProduct(productRequest);
        return new ResponseEntity<Long>(productId, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById( @PathVariable("id") Long productId){
        ProductResponse productResponse=productService.getProductById(productId);
        return  new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
}

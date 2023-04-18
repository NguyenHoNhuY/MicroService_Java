package com.microserviceprojectdemo.ProductService.service;

import com.microserviceprojectdemo.ProductService.model.ProductRequest;
import com.microserviceprojectdemo.ProductService.model.ProductResponse;

public interface ProductService {
    Long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);
}

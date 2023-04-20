package com.microserviceprojectdemo.ProductService.service;


import com.microserviceprojectdemo.ProductService.entity.Product;
import com.microserviceprojectdemo.ProductService.exception.ProductServiceException;
import com.microserviceprojectdemo.ProductService.model.ProductRequest;
import com.microserviceprojectdemo.ProductService.model.ProductResponse;
import com.microserviceprojectdemo.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.copyProperties;


@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired()
    public ProductRepository productRepository;


    @Override
    public Long addProduct(ProductRequest productRequest) {

        log.info("adding product.. ");
        Product product = Product
                .builder()
                .productName(productRequest.getName())
                .productPrice(productRequest.getPrice())
                .productQuantity(productRequest.getQuantity())
                .build();

        productRepository.save(product);

        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductServiceException("Product with give id not found!", "PRODUCT_NOT_FOUND"));


        ProductResponse productResponse = new ProductResponse();

        copyProperties(product, productResponse);

        return productResponse;
    }

    @Override
    public void reduceQuantity(Long productId, Long quantity) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductServiceException("Product with give id not found!", "PRODUCT_NOT_FOUND"));


        if (product.getProductQuantity() < quantity) {
            throw new ProductServiceException("Product does not have sufficient Quantity", "INSUFFICIENT_QUANTITY");

        }

        product.setProductQuantity(product.getProductQuantity()- quantity);
        productRepository.save(product);

        log.info("Product quantity updated successfully");

    }
}

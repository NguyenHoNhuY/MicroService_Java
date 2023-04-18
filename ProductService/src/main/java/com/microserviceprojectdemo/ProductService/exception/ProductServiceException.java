package com.microserviceprojectdemo.ProductService.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductServiceException extends RuntimeException{

    private String errorCode;


    public ProductServiceException(String message , String errorCode){
        super(message);
        this.errorCode=errorCode;
    }
}

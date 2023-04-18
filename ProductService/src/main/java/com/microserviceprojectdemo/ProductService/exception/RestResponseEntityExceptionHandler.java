package com.microserviceprojectdemo.ProductService.exception;

import com.microserviceprojectdemo.ProductService.model.ResponseError;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<ResponseError> handleProductServiceException(ProductServiceException productServiceException) {
        log.info(productServiceException);
        new ResponseError();
        return new ResponseEntity<>(ResponseError
                .builder()
                .message(productServiceException.getMessage())
                .errorCode(productServiceException.getErrorCode())
                .build(),
                HttpStatus.NOT_FOUND);
    }
}



package com.microservicedemo.OrderService.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.microservicedemo.OrderService.external.response.ErrorResponse;


@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OrderServiceException.class)
    public ResponseEntity<ErrorResponse> handleOderServiceException(OrderServiceException orderServiceException ) {
        new ErrorResponse();
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .errorMessage(orderServiceException.getMessage())
                .errorCode(orderServiceException.getErrorCode())
                .build(),
                HttpStatus.NOT_FOUND);
    }
}



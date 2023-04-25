package com.microservicedemo.PaymentService.service;

import com.microservicedemo.PaymentService.model.PaymentRequest;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);
}

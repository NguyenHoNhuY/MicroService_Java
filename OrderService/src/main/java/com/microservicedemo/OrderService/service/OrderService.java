package com.microservicedemo.OrderService.service;

import com.microservicedemo.OrderService.model.OrderRequest;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);
}

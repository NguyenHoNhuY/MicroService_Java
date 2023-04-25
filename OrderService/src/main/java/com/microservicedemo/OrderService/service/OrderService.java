package com.microservicedemo.OrderService.service;

import com.microservicedemo.OrderService.model.OrderRequest;
import com.microservicedemo.OrderService.model.OrderResponse;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(Long orderId);
}

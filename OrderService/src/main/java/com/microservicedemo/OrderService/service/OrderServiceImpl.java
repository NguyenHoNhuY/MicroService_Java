package com.microservicedemo.OrderService.service;

import com.microservicedemo.OrderService.entity.Order;
import com.microservicedemo.OrderService.exception.OrderServiceException;
import com.microservicedemo.OrderService.external.client.PaymentService;
import com.microservicedemo.OrderService.external.client.ProductService;
import com.microservicedemo.OrderService.external.request.PaymentRequest;
import com.microservicedemo.OrderService.model.OrderRequest;
import com.microservicedemo.OrderService.model.OrderResponse;
import com.microservicedemo.OrderService.repository.OrderRepository;
import com.microserviceprojectdemo.ProductService.model.ProductResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Long placeOrder(OrderRequest orderRequest) {

        //* create order
        Order order = Order
                .builder()
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .amount(orderRequest.getTotalAmount())
                .quantity(orderRequest.getQuantity())
                .build();

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
        orderRepository.save(order);


        // do payment
        log.info("Calling Payment Service to complete the payment");
        PaymentRequest paymentRequest = PaymentRequest
                .builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;


        try {

            paymentService.handlePayment(paymentRequest);

            log.info("DO payment successfully. Changing the order status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {

            log.info(e);
            log.info("Error occurred in payment. Changing the order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);

        orderRepository.save(order);


        log.info("order id", order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(Long orderId) {
        Order order = orderRepository
                .findById((orderId))
                .orElseThrow(() -> new OrderServiceException(
                        "Order not found for the order id : " + orderId, "NOT_FOUND", 404));


        log.info("get product from id ");
        ProductResponse productResponse =
                restTemplate.getForObject("http://PRODUCT-SERVICE/product/" + order.getProductId(),
                        ProductResponse.class);

        assert productResponse != null;
        OrderResponse.ProductDetails productDetails =
                OrderResponse.ProductDetails
                        .builder()
                        .productName(productResponse.getProductName())
                        .productId(productResponse.getProductId())
                        .productQuantity(productResponse.getProductQuantity())
                        .productPrice(productResponse.getProductPrice())
                        .build();


        return OrderResponse
                .builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .productDetails(productDetails)
                .build();
    }
}

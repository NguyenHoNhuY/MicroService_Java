package com.microservicedemo.OrderService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;


@Entity
@Table(name = "ORDER_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;


    @Column(name = "Product_Id")
    private Long productId;

    @Column(name = "Product_Quantity")
    private Long quantity;

    @Column(name = "ORDER_DATE")
    private Instant orderDate;

    @Column(name = "ODER_STATUS")
    private String orderStatus;

    @Column(name = "AMOUNT")
    private Long amount;
}

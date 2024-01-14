package com.onlineshop.orderservice.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "Orders")
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderid")
    private Long orderId;


    @Column(name = "ordertime")
    private LocalDateTime dateTime = LocalDateTime.now();

    @Column(name = "totalAmount")
    private double totalAmount;

    @OneToOne
    @JoinColumn(name = "cartId", referencedColumnName = "cartId")
    private Cart cart;

    private long user;

    private long orderPayment;
}

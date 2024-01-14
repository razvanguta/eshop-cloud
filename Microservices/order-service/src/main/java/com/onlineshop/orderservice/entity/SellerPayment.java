package com.onlineshop.orderservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "seller_payment")
@Getter
@Setter
public class SellerPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_track_id")
    private String orderId;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "orderId")
    private Orders orders;

    private long seller;

    private long buyer;

    @OneToOne
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private Items item;

    private double totalAmount;

    private String isPaymentDone;

}

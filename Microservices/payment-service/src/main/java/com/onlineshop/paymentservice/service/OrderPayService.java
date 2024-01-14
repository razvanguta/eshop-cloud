package com.onlineshop.paymentservice.service;

import org.springframework.stereotype.Service;

import com.onlineshop.paymentservice.entity.OrderPayment;
import com.onlineshop.paymentservice.model.RequestOrderPay; 

@Service
public interface OrderPayService {
	OrderPayment saveOrderPay(RequestOrderPay requestOrderPay);
}

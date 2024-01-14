package com.onlineshop.paymentservice.service.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineshop.paymentservice.entity.OrderPayment;
import com.onlineshop.paymentservice.model.RequestOrderPay;
import com.onlineshop.paymentservice.repository.OrderPayRepository;
import com.onlineshop.paymentservice.service.OrderPayService;

@Service
public class OrderPayServiceImpl implements OrderPayService {
	@Autowired
    OrderPayRepository orderPayRepo;
	public OrderPayment saveOrderPay(RequestOrderPay requestOrderPay) {
		 
		OrderPayment orderPayment = new OrderPayment();
		
		orderPayment.setPaymentOption(requestOrderPay.getPaymentOption());
		orderPayment.setTransactionId(requestOrderPay.getTransactionId());
		orderPayment.setPayerId(requestOrderPay.getPayerId());
		orderPayment.setPaymentStatus(requestOrderPay.getPaymentStatus());
		
		return orderPayRepo.save(orderPayment);
	}
}

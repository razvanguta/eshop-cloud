package com.onlineshop.paymentservice.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.paymentservice.entity.OrderPayment;
import com.onlineshop.paymentservice.model.RequestOrderPay;
import com.onlineshop.paymentservice.service.OrderPayService;

@RestController
@RequestMapping("/orderPay")
@CrossOrigin("*")
public class OrderPayController {
	@Autowired
	OrderPayService orderPayService;

	@PostMapping
	public ResponseEntity<OrderPayment> saveOrderPay(@Valid @RequestBody RequestOrderPay requestOrderpay) {
		OrderPayment orderPayment = orderPayService.saveOrderPay(requestOrderpay);
		return new ResponseEntity<>(orderPayment, HttpStatus.CREATED);
	}
}

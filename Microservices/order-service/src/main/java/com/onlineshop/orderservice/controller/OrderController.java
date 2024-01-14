package com.onlineshop.orderservice.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.orderservice.dto.OrderDto;
import com.onlineshop.orderservice.entity.Orders;
import com.onlineshop.orderservice.entity.Payments;
import com.onlineshop.orderservice.entity.SellerPayment;
import com.onlineshop.orderservice.model.RequestOrderPay;
import com.onlineshop.orderservice.model.RequestPaymentModel;
import com.onlineshop.orderservice.service.OrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping
	public ResponseEntity<Orders> saveOrder(@Valid @RequestBody RequestOrderPay requestOrder) {
	 	Orders placeOrder = orderService.placeOrder(requestOrder);
		return new ResponseEntity<>(placeOrder, HttpStatus.CREATED);
	}


	@GetMapping("/{userId}")
	public ResponseEntity<List<OrderDto>> getAllOrders(@PathVariable("userId") long userId) {
		List<OrderDto> orders = orderService.getAllOrders(userId);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Orders>> getAllOrdersForAdmin() {
		List<Orders> orders = orderService.getAllOrdersForAdmin();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/seller/{userId}")
	public ResponseEntity<Map<String, List<SellerPayment>>> getAllOrdersBySeller(@PathVariable("userId") long userId) {
		Map<String, List<SellerPayment>> orders = orderService.getAllOrdersBySeller(userId);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}


	@GetMapping("/seller/payment/{userId}")
	public ResponseEntity<Payments> getAllPaymentsBySeller(@PathVariable("userId") long userId) {
		Payments orders = orderService.getAllPaymentsBySeller(userId);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}


	@PostMapping("/admin/payment")
	public ResponseEntity<Payments> sendPaymentToSeller(@RequestBody RequestPaymentModel data) {
		Payments orders = orderService.sendPaymentsToSeller(data);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
}

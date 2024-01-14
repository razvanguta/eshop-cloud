package com.onlineshop.orderservice.service;

import com.onlineshop.orderservice.dto.OrderDto;
import com.onlineshop.orderservice.entity.Orders;
import com.onlineshop.orderservice.entity.Payments;
import com.onlineshop.orderservice.entity.SellerPayment;
import com.onlineshop.orderservice.model.RequestOrderPay;
import com.onlineshop.orderservice.model.RequestPaymentModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
    Orders placeOrder(RequestOrderPay requestOrder);

    List<OrderDto> getAllOrders(long userId);

    Map<String, List<SellerPayment>> getAllOrdersBySeller(long userId);

    Payments getAllPaymentsBySeller(long userId);

    Payments sendPaymentsToSeller(RequestPaymentModel data);

    List<Orders> getAllOrdersForAdmin();
}

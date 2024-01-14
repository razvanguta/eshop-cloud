package com.onlineshop.orderservice.service;

import com.onlineshop.orderservice.dto.CartDto;
import com.onlineshop.orderservice.entity.Cart;
import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.response.ResponseCart;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    Cart saveCart(RequestItems requestItem) throws Exception;

    CartDto getCartByUser(ResponseCart responseCart);
}

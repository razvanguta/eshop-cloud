package com.onlineshop.orderservice.controller;

import com.onlineshop.orderservice.dto.CartDto;
import com.onlineshop.orderservice.entity.Cart;
import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.response.ResponseCart;
import com.onlineshop.orderservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController

@RequestMapping("/order/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping
    public ResponseEntity<Cart> saveCart(@Valid @RequestBody RequestItems requestitem) throws Exception {
        return new ResponseEntity<>(cartService.saveCart(requestitem), HttpStatus.CREATED);
    }

    @PostMapping("/data")
    public ResponseEntity<CartDto> getAllByUser(@Valid @RequestBody ResponseCart responseCart) {
        CartDto cart = cartService.getCartByUser(responseCart);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}

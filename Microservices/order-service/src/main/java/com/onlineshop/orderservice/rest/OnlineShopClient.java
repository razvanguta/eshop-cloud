package com.onlineshop.orderservice.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.orderservice.dto.OrderPayment;
import com.onlineshop.orderservice.dto.Product;
import com.onlineshop.orderservice.dto.User;
import com.onlineshop.orderservice.model.RequestOrderPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OnlineShopClient extends RestEstablish {
    @Value("${com.app.userById}")
    private String urlByUser;

    @Value("${com.app.productId}")
    private String urlByProduct;

    @Value("${com.app.orderPayment}")
    private String urlByPayment;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public User getUserById(Long userId) {
        String url = UriComponentsBuilder.fromUriString(urlByUser).pathSegment(userId.toString()).build().toString();
        return getRequestForResponse(url);

    }

    public Product getProductById(Long userId) {
        String url = UriComponentsBuilder.fromUriString(urlByProduct).pathSegment(userId.toString()).build().toString();
        return getRestProduct(url);

    }

    public OrderPayment createOrderPayment(RequestOrderPay orderPay) throws JsonProcessingException {
        String url = UriComponentsBuilder.fromUriString(urlByPayment).build().toString();

        return postRequestForResponse(url, objectMapper.writeValueAsString(orderPay), OrderPayment.class);
    }
}

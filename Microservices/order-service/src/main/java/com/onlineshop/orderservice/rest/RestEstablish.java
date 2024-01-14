package com.onlineshop.orderservice.rest;

import com.onlineshop.orderservice.dto.OrderPayment;
import com.onlineshop.orderservice.dto.Product;
import com.onlineshop.orderservice.dto.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


public abstract class RestEstablish {

    abstract RestTemplate getRestTemplate();

    OrderPayment postRequestForResponse(String endpoint, String postBody, Class<OrderPayment> responseType) {
        HttpEntity<String> httpEntity = createHttp(postBody);
        return orderPaymentRest(endpoint, httpEntity, responseType);
    }

    User getRequestForResponse(String endpoint) {
        HttpEntity<String> httpEntity = createHttp(null);
        return userRest(endpoint, httpEntity);
    }

    Product getRestProduct(String endpoint) {
        HttpEntity<String> httpEntity = createHttp(null);
        return productRest(endpoint, httpEntity);
    }

    private User userRest(String endpoint, HttpEntity<?> httpEntity) {
        UriComponents uri = UriComponentsBuilder.fromUriString(endpoint).build();
        ResponseEntity<User> response = getRestTemplate().exchange(uri.toUri(), HttpMethod.GET, httpEntity, User.class);
        return response.getBody();
    }

    private Product productRest(String endpoint, HttpEntity<?> httpEntity) {
        UriComponents uri = UriComponentsBuilder.fromUriString(endpoint).build();
        ResponseEntity<Product> response = getRestTemplate().exchange(uri.toUri(), HttpMethod.GET, httpEntity, Product.class);
        return response.getBody();
    }

    private OrderPayment orderPaymentRest(String endpoint, HttpEntity<?> httpEntity, Class<OrderPayment> responseType) {
        UriComponents uri = UriComponentsBuilder.fromUriString(endpoint).build();
        ResponseEntity<OrderPayment> response = getRestTemplate().exchange(uri.toUri(), HttpMethod.POST, httpEntity, responseType);

        return response.getBody();
    }

    private HttpEntity<String> createHttp(String body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        if (body == null) {
            return new HttpEntity<>(httpHeaders);
        } else {
            return new HttpEntity<>(body, httpHeaders);
        }
    }
}
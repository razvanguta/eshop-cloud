package com.onlineshop.productservice.rest;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


public abstract class RestEstablish {
    protected abstract RestTemplate getRestTemplate();

    protected String getRequestForResponse(String endpoint) {
        HttpEntity<String> httpEntity = createHttp();
        return callRestTemplateUser(endpoint, httpEntity);
    }

    private String callRestTemplateUser(String endpoint, HttpEntity<?> httpEntity) {
        UriComponents uri = UriComponentsBuilder.fromUriString(endpoint).build();
        ResponseEntity<String> response = getRestTemplate().exchange(uri.toUri(), HttpMethod.GET, httpEntity, String.class);

        return response.getBody();
    }

    private HttpEntity<String> createHttp() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        return new HttpEntity<>(httpHeaders);
    }
}
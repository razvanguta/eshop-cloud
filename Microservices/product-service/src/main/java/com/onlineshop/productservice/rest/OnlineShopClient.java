package com.onlineshop.productservice.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.productservice.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OnlineShopClient extends RestEstablish {
    @Value("${com.app.userById}")
    private String urlByUser;

    @Autowired
    private RestTemplate restTemplate;

    @Override
	public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public User getUserById(Long userId) throws JsonProcessingException {
        String url = UriComponentsBuilder.fromUriString(urlByUser).pathSegment(userId.toString()).build().toString();
        return convertStringToUser(getRequestForResponse(url));
    }

    public User convertStringToUser(String data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(data, User.class);
    }
}

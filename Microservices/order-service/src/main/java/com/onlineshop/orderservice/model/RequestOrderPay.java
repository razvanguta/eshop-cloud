package com.onlineshop.orderservice.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class RequestOrderPay {
    @NotBlank(message = "No payment option provided")
    private String paymentOption;

    @NotNull(message = "No cart id provided")
    private Long cartId;

    @NotNull(message = "No user id provided")
    private Long userId;

    private LocalDateTime dateTime = LocalDateTime.now();

    private String paymentStatus;

    private String payerId;

    private String transactionId;
}

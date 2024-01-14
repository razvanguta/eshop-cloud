package com.onlineshop.paymentservice.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestOrderPay {
	@NotBlank(message = "Payment option not provided")
	private String paymentOption;
	@NotNull(message = "Cart id not provided")
	private Long cartId;
	@NotNull(message = "User id not provided")
	private Long userId;
	private LocalDateTime dateTime = LocalDateTime.now();
	private String paymentStatus;
	private String payerId;
	private String transactionId;
}

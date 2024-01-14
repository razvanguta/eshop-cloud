package com.onlineshop.orderservice.response;
 
import javax.validation.constraints.NotNull;

public class ResponseCart {
	@NotNull(message = "No user id provided")
	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}

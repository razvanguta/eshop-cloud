package com.onlineshop.orderservice.model;
  
 
import javax.validation.constraints.NotNull;
 
public class RequestItems {
	@NotNull(message = "No quantity provided")
	private int quantity;

	@NotNull(message = "No product id provided")
	private Long productId;

	@NotNull(message = "No user id provided")
	private Long userId;

	public RequestItems(int quantity, Long productId) {
		this.quantity = quantity;
		this.productId = productId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getQuantity() {
		return quantity;
	}

	public Long getProductId() {
		return productId;
	}
}

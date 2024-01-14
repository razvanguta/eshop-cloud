package com.onlineshop.productservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RequestUpdateProduct {
    @NotBlank
    @JsonProperty("productName")
    private String productName;

    @NotBlank(message = "Please Enter the description")
    @JsonProperty("description")
    private String description;

    @NotNull
    @JsonProperty("stock")
    private Long stock;

    @NotBlank
    @JsonProperty("unit")
    private String unit;
    @NotNull
    @JsonProperty("price")
    private double price;

    @NotNull
    @JsonProperty("categoryId")
    private Long categoryId;

    @NotNull
    @JsonProperty("userId")
    private Long userId;

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

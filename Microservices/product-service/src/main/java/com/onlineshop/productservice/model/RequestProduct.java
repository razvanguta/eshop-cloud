package com.onlineshop.productservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RequestProduct {
    @NotBlank(message = "Please Enter the Product name")
    @JsonProperty("productName")
    private String productName;

    @NotBlank(message = "Please Enter the description")
    @JsonProperty("description")
    private String description;

    @NotBlank(message = "Unit can't be empty")
    @JsonProperty("unit")
    private String unit;

    @NotNull(message = "CategoryId can't be empty")
    @JsonProperty("categoryId")
    private long categoryId;

    @NotNull(message = "userId can't be empty")
    @JsonProperty("userId")
    private long userId;

    @NotNull(message = "price can't be empty")
    @JsonProperty("price")
    private double price;

    public String getProductName() {
        return productName;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }
}

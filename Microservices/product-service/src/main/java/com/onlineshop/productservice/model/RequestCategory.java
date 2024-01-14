package com.onlineshop.productservice.model;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
public class RequestCategory {
    @NotNull
    private Long userId;

    @NotBlank(message = "Please provide a non empty name")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

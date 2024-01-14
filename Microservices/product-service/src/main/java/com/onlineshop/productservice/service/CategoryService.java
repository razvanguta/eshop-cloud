package com.onlineshop.productservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.model.RequestCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category saveCategory(RequestCategory requestCategory) throws JsonProcessingException;

    Category getCategory(Long id);

    Category updatecategory(RequestCategory Category, Long id) throws JsonProcessingException;

    void deleteById(Long id, Long userId) throws JsonProcessingException;

    List<Category> findbyOrder(Long userId) throws JsonProcessingException;

    Category findByCategory(Long id);
}

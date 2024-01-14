package com.onlineshop.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.model.RequestCategory;
import com.onlineshop.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> saveCategory(@Valid @RequestBody RequestCategory requestCategory) throws JsonProcessingException {
        return new ResponseEntity<>(categoryService.saveCategory(requestCategory), HttpStatus.CREATED);
    }

    @GetMapping("/ordercategory/{userId}")
    public ResponseEntity<List<Category>> getFilterCategory(@PathVariable("userId") long userId) throws JsonProcessingException {
        return new ResponseEntity<>(categoryService.findbyOrder(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(categoryService.getCategory(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id, @PathVariable("userId") long userId) throws JsonProcessingException {
        categoryService.deleteById(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody RequestCategory category, @PathVariable("id") long id) throws JsonProcessingException {
        return new ResponseEntity<>(categoryService.updatecategory(category, id), HttpStatus.OK);
    }
}

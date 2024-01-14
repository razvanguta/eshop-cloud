package com.onlineshop.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlineshop.productservice.entity.Product;
import com.onlineshop.productservice.model.RequestApproveData;
import com.onlineshop.productservice.response.ProductResponse;
import com.onlineshop.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController

@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> save(@Valid @RequestPart("data") String requestProduct, @RequestPart(value = "imagefile") MultipartFile file) throws IOException, SQLIntegrityConstraintViolationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(requestProduct, file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getByid(@PathVariable("id") long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);

    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> updateProduct(@Valid @RequestPart("data") String resquestProduct, @RequestPart(value = "imagefile", required = false) MultipartFile file, @PathVariable("id") long id) throws Exception {
        return new ResponseEntity<>(productService.updateProduct(resquestProduct, file, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id, @PathVariable("userId") long userId) throws JsonProcessingException {
        productService.deletebyId(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam("productName") String productName, @RequestParam("userId") Long userId) {
        return new ResponseEntity<>(productService.filterbyId(productName, userId), HttpStatus.OK);
    }

    @GetMapping("/pendingProduct")
    public ResponseEntity<List<ProductResponse>> getAllPendingProduct() {
        List<ProductResponse> productResponseList = productService.getAllPendingPoduct();
        return new ResponseEntity<>(productResponseList, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<?> setProductApproved(@Valid @RequestBody RequestApproveData requestValue) throws JsonProcessingException {
        return new ResponseEntity<>(productService.setStatusApproveProduct(requestValue.getProductId(), requestValue.getUserId(), requestValue.getStatus()), HttpStatus.OK);
    }

}

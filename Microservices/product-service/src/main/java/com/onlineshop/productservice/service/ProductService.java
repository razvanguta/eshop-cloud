package com.onlineshop.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlineshop.productservice.entity.Product;
import com.onlineshop.productservice.response.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public interface ProductService {
    Product saveProduct(String requestProduct, MultipartFile file)
            throws IOException, SQLIntegrityConstraintViolationException;

    void deletebyId(Long id, Long userId) throws JsonProcessingException;

    Product updateProduct(String requestProduct, MultipartFile file, Long id) throws Exception;

    Product getProductById(Long productId);

    List<ProductResponse> filterbyId(String pName, Long userId);

    boolean isAdmin(Long userId) throws JsonProcessingException;

    List<ProductResponse> getAllPendingPoduct();

    Product setStatusApproveProduct(Long productId, Long userId, String status) throws JsonProcessingException;
}

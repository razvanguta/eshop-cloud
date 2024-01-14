package com.onlineshop.productservice.repository;

import com.onlineshop.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductMappingRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);

    List<Product> findAllByStatus(String status);

    List<Product> findAllByStatusOrderByAddedAtDesc(String string);

    List<Product> findAllByStatusAndProductNameContainingOrStatusAndCategoryCategoryNameContaining(String string, String pName, String string2, String pName2);

    Product findByProductId(Long productId);
}

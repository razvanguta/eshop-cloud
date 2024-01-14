package com.onlineshop.orderservice.repository;


import com.onlineshop.orderservice.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface ItemsRepository extends JpaRepository<Items, Long> {
    Set<Items> findByCartCartId(Long cartId);
    Items findByProductAndCartCartId(long productId, Long cartId);
}

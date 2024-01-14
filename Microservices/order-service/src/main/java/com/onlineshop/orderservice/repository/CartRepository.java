package com.onlineshop.orderservice.repository;

import com.onlineshop.orderservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "select * from cart where user=:userId and order_status=:orderStatus", nativeQuery = true)
    Cart findByOrderStatusAndUser(@Param("userId") Long userId, @Param("orderStatus") String orderStatus);
}

package com.onlineshop.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineshop.orderservice.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
	List<Orders> findAllByUserOrderByDateTimeDesc(Long userId);
	List<Orders> findAllByOrderByDateTimeDesc();
}

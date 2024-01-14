package com.onlineshop.orderservice.repository;

import com.onlineshop.orderservice.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    Payments findBySeller(long seller);
}

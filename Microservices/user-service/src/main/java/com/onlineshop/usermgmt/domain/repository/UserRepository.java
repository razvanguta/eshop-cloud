package com.onlineshop.usermgmt.domain.repository;

import com.onlineshop.usermgmt.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmailIgnoreCase(String emailId);

    Users findByEmailIgnoreCaseAndPassword(String emailId, String password);
}

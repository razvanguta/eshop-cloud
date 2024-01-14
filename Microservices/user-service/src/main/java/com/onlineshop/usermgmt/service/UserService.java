package com.onlineshop.usermgmt.service;

import com.onlineshop.usermgmt.domain.Users;
import com.onlineshop.usermgmt.domain.repository.UserRepository;
import com.onlineshop.usermgmt.dto.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Users signUpUser(SignUpRequest signUpRequest) throws Exception {
        if (userRepository.findByEmailIgnoreCase(signUpRequest.getEmail()) != null) {
            throw new Exception("Email already exists");
        }

        Users user = new Users();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setPassword(signUpRequest.getPassword());
        user.setRole(signUpRequest.getRole());
        return userRepository.save(user);
    }

    public Users loginUser(String email, String password) throws Exception {
        Users user = userRepository.findByEmailIgnoreCaseAndPassword(email, password);
        if (user == null) {
            throw new Exception("Invalid credentials");
        }
        return user;
    }

    public Users getUserByUserId(long userId) throws Exception {
        Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new Exception("User not found");
    }
}

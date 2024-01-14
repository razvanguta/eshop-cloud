package com.onlineshop.usermgmt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.usermgmt.domain.Users;
import com.onlineshop.usermgmt.dto.LoginRequest;
import com.onlineshop.usermgmt.dto.SignUpRequest;
import com.onlineshop.usermgmt.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Users> signUpUser(@RequestBody SignUpRequest signUpRequest) throws Exception {
        return ResponseEntity.ok(userService.signUpUser(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<Users> login(@RequestBody LoginRequest request) throws Exception {
        return ResponseEntity.ok(userService.loginUser(request.getEmail(), request.getPassword()));
    }
    
    @GetMapping("/{id}")
	public ResponseEntity<?> getUserByid(@PathVariable("id") long id) throws Exception {
		return new ResponseEntity<>(userService.getUserByUserId(id), HttpStatus.OK);
	}
}

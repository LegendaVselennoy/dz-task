package com.demo.controller;

import com.demo.entity.Customer;
import com.demo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;
    private final PasswordEncoder passwordEncoder;
    private final static String SUCCESS = "Successfully registered";
    private final static String USER_EXIST = "User already exists";

    @PostMapping("/register")
    public ResponseEntity<String> createCustomer(@RequestBody @Validated Customer customer) {
        try {
            String hashPwd = passwordEncoder.encode(customer.getPassword());
            customer.setPassword(hashPwd);
            Customer savedUser = service.saveUser(customer);

            if (savedUser != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESS);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USER_EXIST);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return service.getAllUsers();
    }
}
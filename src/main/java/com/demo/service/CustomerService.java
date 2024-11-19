package com.demo.service;

import com.demo.entity.Customer;
import com.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository userRepository;

    public Customer saveUser(Customer user) {
        return userRepository.save(user);
    }

    public List<Customer> getAllUsers() {
        return userRepository.findAll();
    }
}
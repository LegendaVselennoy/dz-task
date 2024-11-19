package com.demo.service;

import com.demo.entity.Customer;
import com.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User details not found" + username));
        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(user.getRole().toString()));
        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
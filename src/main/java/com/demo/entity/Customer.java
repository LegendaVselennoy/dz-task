package com.demo.entity;

import com.demo.entity.enumerate.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(catalog = "test", schema = "micro", name = "users")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "Email should be valid")
    @NotEmpty(message = "The email must not be empty")
    private String email;
    @NotEmpty(message = "The password must not be empty")
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.ROLE_USER;
}
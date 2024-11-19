package com.demo.config;

import com.demo.converter.KeycloakConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakConverter());
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/user/register", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.PATCH,"/task/patch/{id}").hasRole("USER")
                .requestMatchers(HttpMethod.GET,"/task/{executor}","task").hasRole("USER")
                .requestMatchers("/task/**").hasRole("ADMIN")
                .requestMatchers("/user").authenticated());
        http.csrf(AbstractHttpConfigurer::disable);
        http.oauth2ResourceServer(rsc -> rsc.jwt(jwtConfigurer ->
                jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
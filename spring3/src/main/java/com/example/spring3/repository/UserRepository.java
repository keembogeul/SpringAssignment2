package com.example.spring3.repository;

import com.example.spring3.entity.User;
import com.example.spring3.service.UserDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneWithAuthoritiesByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByPassword(String password);
}
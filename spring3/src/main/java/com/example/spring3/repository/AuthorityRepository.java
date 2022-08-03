package com.example.spring3.repository;

import com.example.spring3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorityRepository extends JpaRepository<User, String> {

}

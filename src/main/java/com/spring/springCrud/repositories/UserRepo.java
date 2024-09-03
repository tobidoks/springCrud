package com.spring.springCrud.repositories;

import com.spring.springCrud.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByUsername(String username);
    List<User> findAllByOrderByRegDateAndTimeDesc();
}

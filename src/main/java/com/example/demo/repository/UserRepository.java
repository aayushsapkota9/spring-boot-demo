package com.example.demo.repository;

import com.example.demo.model.User; // 1. Must import your User entity
import org.springframework.data.jpa.repository.JpaRepository; // 2. Must import JpaRepository
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // It must say "extends JpaRepository<User, Long>"
    Optional<User> findByEmail(String email);
}
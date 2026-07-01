package com.example.demo.repository;

import com.example.demo.model.Teacher;
import com.example.demo.model.User; // 1. Must import your User entity
import org.springframework.data.jpa.repository.JpaRepository; // 2. Must import JpaRepository
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
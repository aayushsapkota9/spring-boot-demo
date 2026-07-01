package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Teacher;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @PostMapping()
    public Teacher createTeacher(@RequestBody Teacher t) {
        return teacherRepository.save(t);
    }

}

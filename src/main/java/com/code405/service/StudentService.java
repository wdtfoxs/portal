package com.code405.service;

import com.code405.entity.model.Group;
import com.code405.entity.model.Student;
import com.code405.repository.models.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Peter Kozlovsky on 16.05.17.
 */
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Student save(Student student, boolean changePassword){
        if(changePassword){
            student.setPassword(passwordEncoder.encode(student.getPassword()));
        }
        return studentRepository.save(student);
    }

    public List<Student> getStudentsByGroup(Group group){
        return studentRepository.findByGroupOrderBySurname(group);
    }
}

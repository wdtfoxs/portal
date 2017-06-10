package com.code405.service;

import com.code405.entity.model.Professor;
import com.code405.entity.util.Select;
import com.code405.repository.models.DisciplineRepository;
import com.code405.repository.models.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;
    private final DisciplineRepository disciplineRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository,
                            PasswordEncoder passwordEncoder, DisciplineRepository disciplineRepository) {
        this.professorRepository = professorRepository;
        this.passwordEncoder = passwordEncoder;
        this.disciplineRepository = disciplineRepository;
    }

    @Transactional
    public Professor save(Professor professor, boolean changePassword) {
        if (changePassword) {
            professor.setPassword(passwordEncoder.encode(professor.getPassword()));
        }
        return professorRepository.save(professor);
    }

    public List<Select> getProfessorsByDisciplineId(Long id) {
        List<Professor> byDisciplines = professorRepository.findByDisciplines(Collections.singleton(disciplineRepository.findOne(id)));
        List<Select> professorList = new ArrayList<>();
        for (Professor professor : byDisciplines) {
            Select select = new Select(false, professor.getId().toString(), professor.getSurname() + " " + professor.getName(), true);
            professorList.add(select);
        }
        return professorList;
    }

}

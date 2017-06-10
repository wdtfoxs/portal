package com.code405.repository.models;

import com.code405.entity.model.Discipline;
import com.code405.entity.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
     List<Professor> findByDisciplines(Set<Discipline> disciplines);
}

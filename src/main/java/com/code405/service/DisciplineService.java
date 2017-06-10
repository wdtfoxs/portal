package com.code405.service;

import com.code405.entity.enumeration.Course;
import com.code405.entity.enumeration.Semester;
import com.code405.entity.model.Discipline;
import com.code405.repository.models.DisciplineRepository;
import com.code405.web.dto.JournalDisciplineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdtfoxs on 27.05.2017.
 */
@Service
public class DisciplineService {
    @Autowired
    private DisciplineRepository disciplineRepository;

    public List<Discipline> disciplinesForJournalOfStudents(Timestamp admission, String group){
        return disciplineRepository.getDisciplineForJournalStudent(Course.getCourse(admission).toString(), group, Semester.getCurrentSemester().toString());
    }

    public List<JournalDisciplineDTO> disciplinesForJournalEmployeeOfStudents(int course, String group){
        List<Discipline> list = disciplineRepository.getDisciplineForJournalStudent(Course.getCourse(course).toString(), group, Semester.getCurrentSemester().toString());
        List<JournalDisciplineDTO> structure = new ArrayList<>();
        JournalDisciplineDTO dto;
        for (Discipline d: list){
            dto = new JournalDisciplineDTO();
            dto.setId(d.getId());
            dto.setDiscipline(d.getDiscipline());
            structure.add(dto);
        }
        return structure;
    }
}

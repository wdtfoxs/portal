package com.code405.service;

import com.code405.entity.model.*;
import com.code405.repository.models.DayBookRepository;
import com.code405.web.dto.JournalDayBookDTO;
import com.code405.web.dto.JournalStudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wdtfoxs on 28.05.2017.
 */
@Service
public class DayBookService {

    @Autowired
    private DayBookRepository dayBookRepository;
    @Autowired
    private StudentService studentService;

    public Map<JournalStudentDTO, Map<Timestamp, String>> getJournal(Group group, Long discipline_id) {
        List<DayBook> journal = dayBookRepository.findStudentsForJournal(discipline_id, group.getGroupCode());
        List<Student> students = studentService.getStudentsByGroup(group);
        Map<JournalStudentDTO, Map<Timestamp, String>> structure = new LinkedHashMap<>();
        Map<Timestamp, String> dayBook;
        Set<Timestamp> time = new TreeSet<>();
        for (DayBook t : journal) {
            time.add(t.getDate());
        }
        int k = 0;
        for (Student s : students) {
            dayBook = new LinkedHashMap<>();
            if (k < journal.size() && s.getId().equals(journal.get(k).getStudent().getId())) {
                for (Timestamp t : time) {
                    if (t.equals(journal.get(k).getDate())) {
                        String temp;
                        if (journal.get(k).getPresence())
                            temp = journal.get(k).getPoints() != 0 ? journal.get(k).getPoints().toString() : "";
                        else
                            temp = journal.get(k).getPoints() != 0 ? journal.get(k).getPoints().toString() + ", н" : "н";
                        dayBook.put(t, temp);

                        k++;
                    } else
                        dayBook.put(t, null);
                }
            } else {
                for (Timestamp t : time) {
                    dayBook.put(t, null);
                }
            }
            JournalStudentDTO student = new JournalStudentDTO(s.getId(), s.getName(), s.getSurname());
            structure.put(student, dayBook);
        }
        return structure;
    }

    @Transactional
    public void saveLessonsResult(List<JournalDayBookDTO> list) throws ParseException {
        List<DayBook> dayBooks = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        DayBook dayBook;
        for (JournalDayBookDTO d : list) {
            dayBook = new DayBook();
            dayBook.setGroup(Group.builder().groupCode(d.getGroup()).build());
            dayBook.setStudent(Student.builder().id(d.getStudent()).build());
            dayBook.setDiscipline(Discipline.builder().id(d.getDiscipline()).build());
            dayBook.setDate(new Timestamp(dateFormat.parse(d.getDate()).getTime()));
            if (d.getPoints() == null)
                dayBook.setPoints(0);
            else
                dayBook.setPoints(d.getPoints());
            dayBook.setPresence(d.isPresence());
            dayBooks.add(dayBook);
        }
        dayBookRepository.save(dayBooks);
    }
}

package com.code405.repository.models;

import com.code405.entity.enumeration.Semester;
import com.code405.entity.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

    @Query(value = "SELECT * FROM discipline AS d " +
            "INNER JOIN curriculum AS cur ON cur.discipline = d.id AND " +
            "cur.course = :course AND " +
            "cur.group_code = :group_code WHERE d.semester = :semester or d.semester = 'EVERY'", nativeQuery = true)
    List<Discipline> getDisciplineForJournalStudent(@Param("course") String course,
                                                    @Param("group_code") String group,
                                                    @Param("semester") String semester);

    List<Discipline> findBySemesterOrSemester(Semester semester, Semester semester2);
    @Query(value = "select distinct t.discipline_id, d.discipline from timetable t " +
            "inner join discipline d on d.id = t.discipline_id " +
            "where group_code = :group_code and (semester= :one or semester= 'EVERY')", nativeQuery = true)
    List<Object> getDisciplinesForExam(@Param("group_code") String groupCode, @Param("one") String one);


    Discipline findByDisciplineAndSemester(String disciplineName, Semester semester);
}

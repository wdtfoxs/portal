package com.code405.repository.models;

import com.code405.entity.enumeration.Class;
import com.code405.entity.enumeration.Week;
import com.code405.entity.enumeration.WeekDay;
import com.code405.entity.model.Group;
import com.code405.entity.model.Professor;
import com.code405.entity.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface TimetableRepository extends JpaRepository<Timetable, Long>, TimetableRepositoryCustom {
    List<Timetable> findAllByGroup(Group group);

    Timetable findByGroupGroupCodeAndDayAndClassNum(String group_groupCode, WeekDay day, Class classNum);

    List<Timetable> findAllByGroupAndDay(Group group, WeekDay day);

    List<Timetable> findAllByProfessorAndDay(Professor professor, WeekDay day);

    List<Timetable> findAllByProfessor(Professor professor);

    @Query(value = "select distinct on (group_code, discipline_id) * from timetable where professor_id = :professor ORDER by group_code asc", nativeQuery = true)
    List<Timetable> findGroupsForProfessor(@Param("professor") Professor professor);

    Timetable findByDayAndClassNumAndAuditoriumIdAndGroupGroupCodeIsNot(WeekDay day, Class classNum, Long auditorium, String group_groupCode);

    Timetable findByWeekAndDayAndGroupGroupCodeIsNotAndProfessorIdAndClassNum(Week week, WeekDay day, String group_groupCode, Long professor_id, Class classNum);

    @Modifying
    @Query(value="delete from Timetable c where c.group = ?1")
    void deleteByGroup(Group group);

    List<Timetable> findByGroupGroupCode(String group);
}

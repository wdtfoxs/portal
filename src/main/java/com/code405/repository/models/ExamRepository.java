package com.code405.repository.models;

import com.code405.entity.model.Exam;
import com.code405.entity.model.Group;
import com.code405.entity.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findExamsByGroupAndTime_FromBetweenOrderByTime_fromAsc(Group group, Timestamp from, Timestamp to);
    List<Exam> findExamsByTimeFromBetween(Timestamp time_from, Timestamp time_from2);

    List<Exam> findExamsByGroupGroupCodeAndTime_FromBetweenOrderByTime_fromAsc(String group, Timestamp from, Timestamp to);

    List<Exam> findExamsByProfessorAndTime_FromBetweenOrderByTime_fromAsc(Professor professor, Timestamp from, Timestamp to);

    List<Exam> findExamsByAuditoriumIdOrProfessorIdOrDisciplineId(Long auditorium_id, Long professor_id, Long discipline_id);

    @Modifying
    @Query(value = "INSERT INTO exam (id,pass_type, date_from, date_to, auditorium_id, discipline_id, group_code, professor_id)" +
            " VALUES (:id,:pass_type, :date_from, :date_to, :auditorium_id, :discipline_id, :group_code, :professor_id)", nativeQuery = true)
    void insert(@Param("id") Long id,@Param("pass_type") String passType, @Param("date_from") Timestamp dateFrom, @Param("date_to") Timestamp dateTo,
                @Param("auditorium_id") Long auditorium, @Param("discipline_id") Long discipline, @Param("group_code") String groupCode, @Param("professor_id") Object professor);


    @Modifying
    @Query(value = "INSERT INTO exam (id,pass_type, date_from, date_to, discipline_id, group_code)" +
            " VALUES (:id,:pass_type, :date_from, :date_to, :discipline_id, :group_code)", nativeQuery = true)
    void insertPhysical(@Param("id") Long id,@Param("pass_type") String passType, @Param("date_from") Timestamp dateFrom, @Param("date_to") Timestamp dateTo,
                 @Param("discipline_id") Long discipline, @Param("group_code") String groupCode);


    @Query(value = "SELECT nextval('hibernate_sequence')", nativeQuery = true)
    Long getSequence();


}



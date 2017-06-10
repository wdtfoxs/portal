package com.code405.repository.models;

import com.code405.entity.model.Exam;
import com.code405.entity.model.ExamResult;
import com.code405.entity.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by hajrullinbulat on 14.05.17.
 */
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    ExamResult findExamResultByExamAndStudent(Exam exam, Student student);

    @Modifying
    @Query(value = "INSERT INTO exam_result (id,points, exam_id, student_id)" +
            " VALUES (:id,:points, :exam_id, :student_id)", nativeQuery = true)
    void insert(@Param("id") Long id, @Param("points") Integer points,
                @Param("exam_id") Long exam_id, @Param("student_id") Long student_id);
}

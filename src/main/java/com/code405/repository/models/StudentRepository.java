package com.code405.repository.models;

import com.code405.entity.model.Group;
import com.code405.entity.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);

    List<Student> findByGroupOrderBySurname(Group group);

   @Query(nativeQuery = true, value = "select student_id from student where group_code = :group_code")
    List<BigInteger> findAllIds(@Param("group_code") String groupCode);
}

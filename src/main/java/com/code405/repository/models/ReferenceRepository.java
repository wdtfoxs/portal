package com.code405.repository.models;

import com.code405.entity.enumeration.Status;
import com.code405.entity.model.Reference;
import com.code405.entity.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface ReferenceRepository extends JpaRepository<Reference, Long> {

    List<Reference> getAllByStudentOrderByCreatedDesc(Student student);
    List<Reference> findByStatusOrderByCreatedDesc(Status status);

    @Modifying
    @Query("update Reference set status = :status where id = :id")
    void updateStatusOfDocument(@Param("id") Long id, @Param("status") Status status);

}

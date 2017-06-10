package com.code405.repository.models;

import com.code405.entity.model.Group;
import com.code405.entity.model.Professor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface GroupRepository extends JpaRepository<Group, String> {
    List<Group> findByCreated(Date created, Sort sort);

    @Query(value = "select distinct on (group_code) * from timetable where professor_id = :professor", nativeQuery = true)
    List<Group> findGroupsForProfessor(@Param("professor") Professor professor);
}

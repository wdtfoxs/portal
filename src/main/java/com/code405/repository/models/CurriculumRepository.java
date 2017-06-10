package com.code405.repository.models;

import com.code405.entity.enumeration.Course;
import com.code405.entity.model.Curriculum;
import com.code405.entity.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    List<Curriculum> findAllByGroupAndCourse(Group group, Course course);


    @Modifying
    @Query(value ="delete from curriculum c where c.course = :course and c.group_code = :group_code", nativeQuery = true)
    void deleteByCourseAndGroup(@Param("course") String course,
                       @Param("group_code") String groupCode);
}

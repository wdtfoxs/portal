package com.code405.repository.models;

import com.code405.entity.model.DayBook;
import com.code405.entity.model.Discipline;
import com.code405.entity.model.Group;
import com.code405.entity.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface DayBookRepository extends JpaRepository<DayBook, Long> {
    List<DayBook> findDayBooksByStudentAndDisciplineAndDateBetween(Student student, Discipline discipline, Timestamp from, Timestamp to);

    @Query(value = "SELECT\n" +
            "  avg(sum_points_by_discipline + exam_points)\n" +
            "FROM (SELECT\n" +
            "        sum(d_b.points)   AS sum_points_by_discipline,\n" +
            "        ex_r.points       AS exam_points,\n" +
            "        d_b.discipline_id AS discipline_id,\n" +
            "        d_b.student_id    AS student_id\n" +
            "      FROM\n" +
            "        day_book d_b\n" +
            "        INNER JOIN exam ex ON d_b.discipline_id = ex.discipline_id AND d_b.group_code = ex.group_code\n" +
            "        INNER JOIN exam_result ex_r ON ex.id = ex_r.exam_id AND ex_r.student_id = d_b.student_id\n" +
            "      WHERE d_b.group_code = :group_code AND d_b.discipline_id = :discipline_id\n" +
            "        AND d_b.date BETWEEN :from AND :to\n" +
            "      GROUP BY d_b.discipline_id, d_b.student_id, ex_r.points\n" +
            "     ) inner_by_discipline\n" +
            "GROUP BY inner_by_discipline.discipline_id"
            , nativeQuery = true)
    Double selectAvgPointsForGroup(
            @Param("discipline_id") Long disciplineId,
            @Param("group_code") String groupCode,
            @Param("from") Timestamp from,
            @Param("to") Timestamp to
    );

    @Query(value = "SELECT\n" +
            "  avg(sum_points_by_discipline + exam_points)\n" +
            "FROM (SELECT\n" +
            "        sum(d_b.points)   AS sum_points_by_discipline,\n" +
            "        ex_r.points       AS exam_points,\n" +
            "        d_b.discipline_id AS discipline_id,\n" +
            "        d_b.student_id    AS student_id\n" +
            "      FROM\n" +
            "        day_book d_b\n" +
            "        INNER JOIN exam ex ON d_b.discipline_id = ex.discipline_id\n" +
            "        INNER JOIN exam_result ex_r ON ex.id = ex_r.exam_id AND ex_r.student_id = d_b.student_id\n" +
            "      WHERE d_b.discipline_id = :discipline_id AND d_b.date BETWEEN :from AND :to\n" +
            "      GROUP BY d_b.discipline_id, d_b.student_id, ex_r.points\n" +
            "     ) inner_by_discipline\n" +
            "GROUP BY inner_by_discipline.discipline_id"
            , nativeQuery = true)
    Double selectAvgPointsForCourse(
            @Param("discipline_id") Long disciplineId,
            @Param("from") Timestamp from,
            @Param("to") Timestamp to
    );

    @Query(value = "SELECT student_rate.rating\n" +
            "FROM (\n" +
            "       SELECT\n" +
            "         inner_rating.student_id                 AS student_id,\n" +
            "         inner_rating.avg_point                  AS discipline_avg_point,\n" +
            "         row_number()\n" +
            "         OVER (\n" +
            "           ORDER BY inner_rating.avg_point DESC) AS rating\n" +
            "       FROM (\n" +
            "              SELECT\n" +
            "                avg(inner_avg_point.discipline_avg_point) AS avg_point,\n" +
            "                inner_avg_point.student_id                AS student_id\n" +
            "              FROM (\n" +
            "                     SELECT\n" +
            "                       inner_by_discipline.student_id       AS student_id,\n" +
            "                       avg(inner_by_discipline.sum_points_by_discipline +\n" +
            "                           inner_by_discipline.exam_points) AS discipline_avg_point,\n" +
            "                       inner_by_discipline.discipline_id    AS discipline_id\n" +
            "                     FROM (\n" +
            "                            SELECT\n" +
            "                              sum(d_b.points)   AS sum_points_by_discipline,\n" +
            "                              ex_r.points       AS exam_points,\n" +
            "                              d_b.student_id    AS student_id,\n" +
            "                              d_b.discipline_id AS discipline_id\n" +
            "                            FROM\n" +
            "                              day_book d_b\n" +
            "                              INNER JOIN exam ex ON d_b.discipline_id = ex.discipline_id\n" +
            "                              INNER JOIN exam_result ex_r ON ex.id = ex_r.exam_id AND ex_r.student_id = d_b.student_id\n" +
            "                            WHERE d_b.date BETWEEN :from AND :to AND d_b.group_code = :group_code\n" +
            "                            GROUP BY d_b.discipline_id, d_b.student_id, ex_r.points\n" +
            "                          ) inner_by_discipline\n" +
            "                     GROUP BY inner_by_discipline.student_id, inner_by_discipline.discipline_id\n" +
            "                   ) inner_avg_point\n" +
            "              GROUP BY inner_avg_point.student_id\n" +
            "            ) inner_rating\n" +
            "       GROUP BY inner_rating.student_id, inner_rating.avg_point\n" +
            "     ) student_rate\n" +
            "\n" +
            "WHERE student_rate.student_id = :stud_id  --тут ид студента\n" +
            ";"
            , nativeQuery = true)
    Double getStudentPlaceInGroup(
            @Param("stud_id") Long studentId,
            @Param("group_code") String groupCode,
            @Param("from") Timestamp from,
            @Param("to") Timestamp to
    );

    @Query(value = "SELECT student_rate.rating\n" +
            "FROM (\n" +
            "       SELECT\n" +
            "         inner_rating.student_id                 AS student_id,\n" +
            "         inner_rating.avg_point                  AS discipline_avg_point,\n" +
            "         row_number()\n" +
            "         OVER (\n" +
            "           ORDER BY inner_rating.avg_point DESC) AS rating\n" +
            "       FROM (\n" +
            "              SELECT\n" +
            "                avg(inner_avg_point.discipline_avg_point) AS avg_point,\n" +
            "                inner_avg_point.student_id                AS student_id\n" +
            "              FROM (\n" +
            "                     SELECT\n" +
            "                       inner_by_discipline.student_id       AS student_id,\n" +
            "                       avg(inner_by_discipline.sum_points_by_discipline +\n" +
            "                           inner_by_discipline.exam_points) AS discipline_avg_point,\n" +
            "                       inner_by_discipline.discipline_id    AS discipline_id\n" +
            "                     FROM (\n" +
            "                            SELECT\n" +
            "                              sum(d_b.points)   AS sum_points_by_discipline,\n" +
            "                              ex_r.points       AS exam_points,\n" +
            "                              d_b.student_id    AS student_id,\n" +
            "                              d_b.discipline_id AS discipline_id\n" +
            "                            FROM\n" +
            "                              day_book d_b\n" +
            "                              INNER JOIN exam ex ON d_b.discipline_id = ex.discipline_id\n" +
            "                              INNER JOIN exam_result ex_r ON ex.id = ex_r.exam_id AND ex_r.student_id = d_b.student_id\n" +
            "                            WHERE d_b.date BETWEEN :from AND :to\n" +
            "                            GROUP BY d_b.discipline_id, d_b.student_id, ex_r.points\n" +
            "                          ) inner_by_discipline\n" +
            "                     GROUP BY inner_by_discipline.student_id, inner_by_discipline.discipline_id\n" +
            "                   ) inner_avg_point\n" +
            "              GROUP BY inner_avg_point.student_id\n" +
            "            ) inner_rating\n" +
            "       GROUP BY inner_rating.student_id, inner_rating.avg_point\n" +
            "     ) student_rate\n" +
            "\n" +
            "WHERE student_rate.student_id = :stud_id  --тут ид студента\n" +
            ";"
            , nativeQuery = true)
    Double getStudentPlaceInCourse(
            @Param("stud_id") Long studentId,
            @Param("from") Timestamp from,
            @Param("to") Timestamp to
    );

    @Query(value = "select * from day_book as b inner join student as st on st.student_id = b.student_id and " +
            "st.group_code = b.group_code inner join users as u on u.id = st.student_id " +
            "where b.discipline_id = :discipline_id and b.group_code = :group_code order by u.surname, b.date asc",
            nativeQuery = true)
    List<DayBook> findStudentsForJournal(@Param("discipline_id") Long discipline_id, @Param("group_code") String group_code);
}

package com.code405.repository.models;

import com.code405.entity.model.Auditorium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {

    List<Auditorium> findByBuildingId(Long building_id);

    @Query(value = "SELECT\n" +
            "  a.id,\n" +
            "  a.number,\n" +
            "  a.capacity,\n" +
            "  a.building_id\n" +
            "FROM auditorium a\n" +
            "WHERE a.id NOT IN\n" +
            "      (SELECT t.auditorium_id\n" +
            "       FROM timetable t\n" +
            "       WHERE t.class = :class AND t.day = :day AND (t.week = 'Каждая' OR t.week = :week)\n" +
            "             AND t.auditorium_id IS NOT NULL\n" +
            "      );", nativeQuery = true)
    List<Auditorium> findAllFreeAuditories(
            @Param("class") String time,
            @Param("day") String day,
            @Param("week") String week
    );
}

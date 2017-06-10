package com.code405.entity.model;

import com.code405.entity.enumeration.Class;
import com.code405.entity.enumeration.Type;
import com.code405.entity.enumeration.Week;
import com.code405.entity.enumeration.WeekDay;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "timetable", uniqueConstraints = {@UniqueConstraint(columnNames = {"class", "day", "group_code"})})
public class Timetable implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "class", nullable = false)
    private Class classNum;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Week week;

    @Enumerated(EnumType.STRING)
    @Column
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WeekDay day;

    @Column(name = "comment")
    private String comment = "";

    @ManyToOne
    @JoinColumn(name = "group_code")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditorium;
}

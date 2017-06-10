package com.code405.entity.model;

/**
 * Created by hajrullinbulat on 14.05.17.
 */

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "day_book")
public class DayBook {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_code")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "presence", nullable = false)
    private Boolean presence;

    @Column(name = "points", nullable = false)
    private Integer points;
}

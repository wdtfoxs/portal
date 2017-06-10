package com.code405.entity.model;

import com.code405.entity.enumeration.Course;
import com.code405.entity.enumeration.PassType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "curriculum")
public class Curriculum implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "lec_amount", nullable = false)
    private Integer lectureAmount;

    @Column(name = "prac_amount", nullable = false)
    private Integer practiceAmount;

    @Column(name = "lab_amount", nullable = false)
    private Integer labAmount;

    @Column(name = "pass_type", nullable = false)
    private PassType passType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_code")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline")
    private Discipline discipline;

    @Enumerated(EnumType.STRING)
    private Course course;

}

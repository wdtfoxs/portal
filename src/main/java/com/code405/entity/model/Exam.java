package com.code405.entity.model;

import com.code405.entity.embeddable.Time;
import com.code405.entity.enumeration.PassType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "exam")
public class Exam implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "pass_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PassType passType;

    @Embedded
    private Time time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_code")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditorium;

}

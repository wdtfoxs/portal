package com.code405.entity.model;

import com.code405.entity.enumeration.ReferenceType;
import com.code405.entity.enumeration.Status;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Table(name = "reference")
public class Reference {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReferenceType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private Timestamp created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
}

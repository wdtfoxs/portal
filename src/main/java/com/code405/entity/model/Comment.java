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
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_code")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "comment", nullable = false)
    private String comment;
}

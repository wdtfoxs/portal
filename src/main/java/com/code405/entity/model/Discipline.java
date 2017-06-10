package com.code405.entity.model;

import com.code405.entity.enumeration.Semester;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "discipline",  uniqueConstraints = {@UniqueConstraint(columnNames = {"discipline", "semester"})})
public class Discipline implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "discipline", nullable = false)
    private String discipline;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;

    @Column(name = "physical_culture")
    private boolean physicalCulture;

    @ManyToMany(mappedBy = "disciplines", fetch = FetchType.EAGER)
    private List<Professor> professors;

}

package com.code405.entity.model;

import lombok.*;

import javax.persistence.*;

/**
 * Created by hajrullinbulat on 10.05.17.
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "auditorium", uniqueConstraints = {@UniqueConstraint(columnNames = {"number", "building_id"})})
public class Auditorium {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @Column(name = "capacity")
    private Integer capacity;

}

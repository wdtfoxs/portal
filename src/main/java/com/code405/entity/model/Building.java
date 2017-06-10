package com.code405.entity.model;

import com.code405.entity.embeddable.Address;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "building")
public class Building {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "building", fetch = FetchType.EAGER)
    private List<Auditorium> auditoriums;

}


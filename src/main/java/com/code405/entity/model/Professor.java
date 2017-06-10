package com.code405.entity.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "professor")
@PrimaryKeyJoinColumn(name = "professor_id", referencedColumnName = "id")
public class Professor extends User {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "professor_discipline",
            joinColumns = {@JoinColumn(name = "professor_id", referencedColumnName = "professor_id")},
            inverseJoinColumns = {@JoinColumn(name = "discipline_id", referencedColumnName = "id")})
    private Set<Discipline> disciplines = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "professor_position",
            joinColumns = {@JoinColumn(name = "professor_id", referencedColumnName = "professor_id")},
            inverseJoinColumns = {@JoinColumn(name = "position_id", referencedColumnName = "id")})
    private Set<Position> positions = new HashSet<>();

    @Builder
    public Professor(Long id, String username, String email, String name, String surname, String msisdn, Date birthday, boolean sex, String password, boolean enabled, int version, Set<Role> roles, Set<Discipline> disciplines, Set<Position> positions) {
        super(id, username, email, name, surname, msisdn, birthday, sex, password, enabled, version, roles);
        this.disciplines = disciplines;
        this.positions = positions;
    }
}

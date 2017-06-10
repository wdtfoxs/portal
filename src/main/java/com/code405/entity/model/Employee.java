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
@Table(name = "employee")
@PrimaryKeyJoinColumn(name = "employee_id", referencedColumnName = "id")
public class Employee extends User {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employee_position",
            joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "position_id", referencedColumnName = "id")})
    private Set<Position> positions = new HashSet<>();

    @Builder
    public Employee(Long id, String username, String email, String name, String surname, String msisdn, Date birthday, boolean sex, String password, boolean enabled, int version, Set<Role> roles) {
        super(id, username, email, name, surname, msisdn, birthday, sex, password, enabled, version, roles);
    }
}

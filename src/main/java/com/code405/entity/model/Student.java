package com.code405.entity.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "student_id", referencedColumnName = "id")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "group_code")
public class Student extends User {
    @ManyToOne
    @JsonProperty("group_code")
    @JoinColumn(name = "group_code", nullable = false)
    private Group group;

    @Column(name = "admission_date", nullable = false)
    private Timestamp admissionDate;

    @Builder
    public Student(Long id, String username, String email, String name, String surname, String msisdn, Date birthday, boolean sex, String password, boolean enabled, int version, Set<Role> roles, Group group, Timestamp admissionDate) {
        super(id, username, email, name, surname, msisdn, birthday, sex, password, enabled, version, roles);
        this.group = group;
        this.admissionDate = admissionDate;
    }
}

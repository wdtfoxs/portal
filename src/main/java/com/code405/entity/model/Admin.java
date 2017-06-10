package com.code405.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

/**
 * Created by Peter Kozlovsky on 23.05.17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "admin_id", referencedColumnName = "id")
public class Admin extends User {

    @Builder
    public Admin(Long id, String username, String email, String name, String surname, String msisdn, Date birthday, boolean sex, String password, boolean enabled, int version, Set<Role> roles) {
        super(id, username, email, name, surname, msisdn, birthday, sex, password, enabled, version, roles);
    }


}
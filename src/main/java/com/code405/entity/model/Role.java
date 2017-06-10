package com.code405.entity.model;

import com.code405.entity.enumeration.RoleEnumeration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by birth on 12.02.2017.
 */
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleEnumeration name;

    public Role(RoleEnumeration name) {
        this.name = name;
    }

}

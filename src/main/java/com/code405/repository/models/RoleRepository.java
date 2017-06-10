package com.code405.repository.models;

import com.code405.entity.enumeration.RoleEnumeration;
import com.code405.entity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by birth on 12.02.2017.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleEnumeration name);
}

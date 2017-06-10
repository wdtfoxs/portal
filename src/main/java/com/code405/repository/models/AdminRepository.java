package com.code405.repository.models;

import com.code405.entity.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Peter Kozlovsky on 23.05.17.
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
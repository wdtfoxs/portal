package com.code405.repository.models;

import com.code405.entity.model.Auditorium;
import com.code405.entity.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

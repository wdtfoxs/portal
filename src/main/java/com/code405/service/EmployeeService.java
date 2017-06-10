package com.code405.service;

import com.code405.entity.model.Employee;
import com.code405.repository.models.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Peter Kozlovsky on 20.05.17.
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public Employee save(Employee employee, boolean changePassword){
        if(changePassword){
            employee.setPassword(encoder.encode(employee.getPassword()));
        }
        return employeeRepository.save(employee);
    }
}

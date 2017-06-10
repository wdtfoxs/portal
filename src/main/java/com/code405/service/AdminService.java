package com.code405.service;

import com.code405.entity.model.Admin;
import com.code405.repository.models.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Peter Kozlovsky on 23.05.17.
 */
@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(Admin admin, boolean changePassword) {
        if (changePassword) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        }
        adminRepository.save(admin);
    }
}
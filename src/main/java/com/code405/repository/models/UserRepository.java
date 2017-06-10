package com.code405.repository.models;


import com.code405.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    User findByEmailOrUsername(String email, String username);

}

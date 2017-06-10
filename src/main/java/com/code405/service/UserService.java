package com.code405.service;


import com.code405.entity.model.User;
import com.code405.repository.models.RoleRepository;
import com.code405.repository.models.UserRepository;
import com.code405.service.interfaces.IUserService;
import com.code405.validation.UserAlreadyExistException;
import com.code405.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Birthright on 02.05.2016.
 */
@Service
public class UserService implements  IUserService {
    private static final String OK = "OK";
    private static final String EMAIL_EXISTS = "EMAIL_EXISTS";
    private static final String USERNAME_EXISTS = "USERNAME_EXISTS";


    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User createUserAccount(UserDto accountDto) {
        User registered;
        try {
            registered = registerNewUserAccount(accountDto);
        } catch (UserAlreadyExistException e) {
            return null;
        }
        return registered;
    }

    private User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        String result = checkUserIsExists(userDto.getUsername(), userDto.getEmail());
        switch (result) {
            case EMAIL_EXISTS:
                throw new UserAlreadyExistException("Account with this email already exists" + userDto.getEmail());
            case USERNAME_EXISTS:
                throw new UserAlreadyExistException("Account with this username already exists" + userDto.getUsername());
        }
//       // User user = User.builder()
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .username(userDto.getUsername())
//                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.STUDENT)))
//                .build();
        return save(null, true);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public String checkUserIsExists(String username, String email) {
        User user = userRepository.findByEmailOrUsername(email, username);
        if (user == null) {
            return OK;
        }
        if (user.getEmail().equals(email)) {
            return EMAIL_EXISTS;
        }
        return USERNAME_EXISTS;
    }

    @Override
    @Transactional
    public User save(User user, boolean changePassword) {
        if (changePassword) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User changeUserPassword(User user, String password) {
        user.setPassword(encoder.encode(password));
        return userRepository.save(user);
    }

}

package com.code405.service.interfaces;


import com.code405.entity.model.User;
import com.code405.web.dto.UserDto;

/**
 * Created by birth on 19.02.2017.
 */
public interface IUserService {
    User createUserAccount(UserDto accountDto);

    String checkUserIsExists(String username, String email);

    User save(User user, boolean changePassword);

    User findUserByEmail(String email);

    User changeUserPassword(User user, String password);

}

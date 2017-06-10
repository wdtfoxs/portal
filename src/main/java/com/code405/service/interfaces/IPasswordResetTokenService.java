package com.code405.service.interfaces;


import com.code405.entity.PasswordResetToken;
import com.code405.entity.model.User;

/**
 * Created by birthright on 25.02.17.
 */
public interface IPasswordResetTokenService {
    PasswordResetToken findPasswordResetToken(String token);

    PasswordResetToken createPasswordResetToken(String token, User user);

    void deletePasswordResetToken(String token);

    void deletePasswordResetToken(User user);
}

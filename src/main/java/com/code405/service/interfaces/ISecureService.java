package com.code405.service.interfaces;

import com.code405.entity.PasswordResetToken;

/**
 * Created by birthright on 28.02.17.
 */
public interface ISecureService {

    String checkConfirmResetPasswordToken(PasswordResetToken resetToken, Long id);
}

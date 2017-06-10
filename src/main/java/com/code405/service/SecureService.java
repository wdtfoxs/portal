package com.code405.service;


import com.code405.entity.PasswordResetToken;
import com.code405.service.interfaces.ISecureService;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by birthright on 28.02.17.
 */
@Service
public class SecureService implements ISecureService {
    @Override
    public String checkConfirmResetPasswordToken(PasswordResetToken resetToken, Long id) {
        if (resetToken == null || !resetToken.getUser().getId().equals(id)) {
            return "Недействительный токен";
        }
        Calendar calendar = Calendar.getInstance();
        if (resetToken.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
            return "Время действия токена истекло";
        }
        return null;
    }

}

package com.code405.service;

import com.code405.entity.PasswordResetToken;
import com.code405.entity.model.User;
import com.code405.repository.PasswordResetTokenRepository;
import com.code405.service.interfaces.IPasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by birthright on 26.02.17.
 */
@Service
public class PasswordResetTokenService implements IPasswordResetTokenService {
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PasswordResetToken findPasswordResetToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public PasswordResetToken createPasswordResetToken(String token, User user) {
        PasswordResetToken resetToken = tokenRepository.findByUser(user);
        if (resetToken != null) {
            resetToken.setToken(token);
            resetToken.setExpiryDate();
        } else {
            resetToken = new PasswordResetToken(token, user);
        }
        return tokenRepository.save(resetToken);
    }

    @Override
    @Transactional
    public void deletePasswordResetToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    @Override
    @Transactional
    public void deletePasswordResetToken(User user) {
        tokenRepository.deleteByUser(user);
    }
}

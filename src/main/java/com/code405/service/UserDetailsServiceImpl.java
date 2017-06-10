package com.code405.service;

import com.code405.entity.model.User;
import com.code405.repository.models.UserRepository;
import com.code405.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

/**
 * Created by Peter Kozlovsky on 16.05.17.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = Pattern.compile(EMAIL_PATTERN).matcher(login).matches() ?
                userRepository.findByEmail(login) : userRepository.findByUsername(login);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with this email/username: " + login);
        }
        return new UserDetailsImpl(user);
    }
}

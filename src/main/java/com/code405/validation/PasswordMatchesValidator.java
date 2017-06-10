package com.code405.validation;



import com.code405.web.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by birth on 12.02.2017.
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDto> {

    public void initialize(PasswordMatches constraint) {

    }

    @Override
    public boolean isValid(UserDto user, ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getMatchingPassword());
    }

}

package com.example.unistore.util;

import com.example.unistore.models.User;
import com.example.unistore.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.findByUserLogin(user) != null) {
            errors.rejectValue("userLogin", "", "Нельзя использовать этот логин");
        }
    }
}

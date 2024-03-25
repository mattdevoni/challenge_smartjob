package com.challenge.demo.controller.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Validators {

    @Value("${expreg.email}")
    private String expregEmail;
    @Value("${expreg.password}")
    private String expregPassword;

    public boolean validateEmail(String email) {
        return email.matches(expregEmail);
    }

    public boolean validatePassword(String password) {
        return password.matches(expregPassword);
    }
}

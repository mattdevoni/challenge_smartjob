package com.challenge.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExistUserException extends RuntimeException {
    private final String code = "EXIST_USER_003";
    private final String message = "The email is already registered";
}


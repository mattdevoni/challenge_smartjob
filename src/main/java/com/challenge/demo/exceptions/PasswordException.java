package com.challenge.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordException extends RuntimeException {
    private final String code = "PASS_INVALID_001";
    private final String message = "Invalid password. Password must contain at least one uppercase letter, two digits, and one lowercase letter";
}

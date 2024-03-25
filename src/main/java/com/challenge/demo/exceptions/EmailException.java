package com.challenge.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailException extends RuntimeException {
    private final String code = "EMAIL_INVALID_001";
    private final String message = "Invalid email address. Please enter a valid email address. (aaaaaaa@dominio.cl)";
}


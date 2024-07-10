package com.example.demo.Security;

import org.springframework.security.core.AuthenticationException;

public class InvalidEmailException extends AuthenticationException {
    public InvalidEmailException(String msg) {
        super(msg);
    }
}

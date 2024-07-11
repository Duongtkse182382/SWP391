package com.example.demo.Exception;

public class DuplicateCounterNameException extends RuntimeException {
    public DuplicateCounterNameException(String message) {
        super(message);
    }
}
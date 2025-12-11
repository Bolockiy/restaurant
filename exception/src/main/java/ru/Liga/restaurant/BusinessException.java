package ru.Liga.restaurant;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
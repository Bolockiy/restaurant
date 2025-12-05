package ru.Liga;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
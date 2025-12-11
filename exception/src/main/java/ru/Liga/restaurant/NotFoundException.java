package ru.Liga.restaurant;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
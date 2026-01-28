package ru.ivamly.bumazhka.plate;

public class NotValidException extends RuntimeException {
    public NotValidException(String message) {
        super(message);
    }
}

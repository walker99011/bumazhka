package ru.tooloolooz.bumazhka;

public class NotValidException extends RuntimeException {
    public NotValidException(final String message) {
        super(message);
    }
}

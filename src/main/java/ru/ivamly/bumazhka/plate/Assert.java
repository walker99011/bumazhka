package ru.ivamly.bumazhka.plate;

public class Assert {
    public static void notEmpty(final CharSequence sequence, final String message) {
        if (sequence == null || sequence.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}

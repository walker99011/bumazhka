package ru.ivamly.bumazhka.plate;

public interface VehiclePlateValidator {
    void validate(String plate) throws NotValidException;

    boolean isValid(String plate);

    VehiclePlateType getType();
}

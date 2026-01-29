package ru.tooloolooz.bumazhka.plate;

public abstract class AbstractPlateValidator {

    /**
     * aajlsfghasjkg
     * @param plate
     * @return
     */
    public abstract boolean isValid(String plate);

    public abstract VehiclePlateType getType();
}

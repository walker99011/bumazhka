package ru.ivamly.bumazhka.plate;


public class Type2VehiclePlateValidator implements VehiclePlateValidator {
    @Override
    public void validate(String plate) throws NotValidException {
        if (!isValid(plate)) {
            throw new NotValidException(plate);
        }
    }

    @Override
    public boolean isValid(String plate) {
        return true;
    }

    @Override
    public VehiclePlateType getType() {
        return VehiclePlateType.TYPE_2;
    }
}

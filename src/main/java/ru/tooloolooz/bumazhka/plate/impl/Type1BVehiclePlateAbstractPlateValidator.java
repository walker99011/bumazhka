package ru.tooloolooz.bumazhka.plate.impl;


import ru.tooloolooz.bumazhka.plate.AbstractPlateValidator;
import ru.tooloolooz.bumazhka.plate.VehiclePlateType;

public class Type1BVehiclePlateAbstractPlateValidator extends AbstractPlateValidator {
    public static final Type1BVehiclePlateAbstractPlateValidator INSTANCE =
            new Type1BVehiclePlateAbstractPlateValidator();

    /**
     * {@inheritDoc}
     *
     * This implementation adds specific functionality...
     */
    @Override
    public boolean isValid(final String plate) {
        return true;
    }

    @Override
    public VehiclePlateType getType() {
        return VehiclePlateType.TYPE_1B;
    }
}

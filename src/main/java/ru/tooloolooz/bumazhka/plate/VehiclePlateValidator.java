package ru.tooloolooz.bumazhka.plate;

import ru.tooloolooz.bumazhka.NotValidException;
import ru.tooloolooz.bumazhka.plate.impl.Type1BVehiclePlateAbstractPlateValidator;
import ru.tooloolooz.bumazhka.plate.impl.Type1VehiclePlateAbstractPlateValidator;

/**
 * This utility class provides validation for Russian Federation vehicle state registration plates.
 * Allowed formats: М000ММ55 or М000ММ555.
 * 0 - digit indicating the number
 * M - letter indicating the series
 * 5 - digit of the region code
 *
 * @see <a href="https://docs.cntd.ru/document/1200160380"/>
 */
public final class VehiclePlateValidator {
    /**
     * Instance of 1 type vehicle state registration plate validator.
     */
    private static final Type1VehiclePlateAbstractPlateValidator TYPE_1_VEHICLE_PLATE_VALIDATOR = Type1VehiclePlateAbstractPlateValidator.INSTANCE;

    /**
     * Instance of 1b type vehicle state registration plate validator.
     */
    private static final Type1BVehiclePlateAbstractPlateValidator TYPE_1B_VEHICLE_PLATE_VALIDATOR = Type1BVehiclePlateAbstractPlateValidator.INSTANCE;

    /**
     * Error message.
     */
    private final static String EXCEPTION_MESSAGE = "Invalid vehicle state registration plate: ";

    /**
     * Validates vehicle state registration plate.
     *
     * @param plate vehicle state registration plate.
     * @throws NotValidException if {@code plate} is {@code null} or {@code invalid}.
     */
    public static void validate(final String plate) throws NotValidException {
        if (!isValid(plate)) {
            throw new NotValidException(EXCEPTION_MESSAGE + plate);
        }
    }

    /**
     * Validates vehicle state registration plate.
     *
     * @param plate vehicle state registration plate.
     * @param type  vehicle state registration plate {@link VehiclePlateType type}.
     * @throws NotValidException if {@code plate} is {@code null} or {@code invalid}.
     */
    public static void validate(final String plate, final VehiclePlateType type) throws NotValidException {
        if (!isValid(plate, type)) {
            throw new NotValidException(EXCEPTION_MESSAGE + plate);
        }
    }

    /**
     * Validates vehicle state registration plate.
     *
     * @param plate vehicle state registration plate.
     * @return {@code true} if the plate is valid, {@code false} otherwise.
     */
    public static boolean isValid(final String plate) {
        return TYPE_1_VEHICLE_PLATE_VALIDATOR.isValid(plate)
               || TYPE_1B_VEHICLE_PLATE_VALIDATOR.isValid(plate);
    }

    /**
     * Validates vehicle state registration plate.
     *
     * @param plate vehicle state registration plate.
     * @param type  vehicle state registration plate {@link VehiclePlateType type}.
     * @return {@code true} if the plate is valid, {@code false} otherwise.
     */
    public static boolean isValid(final String plate, final VehiclePlateType type) {
        return getValidator(type).isValid(plate);
    }

    /**
     * @param type vehicle state registration plate {@link VehiclePlateType type}.
     * @return instance of plate validator based on plate {@link VehiclePlateType type}.
     */
    private static AbstractPlateValidator getValidator(VehiclePlateType type) {
        return switch (type) {
            case TYPE_1, TYPE_1A -> TYPE_1_VEHICLE_PLATE_VALIDATOR;
            case TYPE_1B -> TYPE_1B_VEHICLE_PLATE_VALIDATOR;
        };
    }
}

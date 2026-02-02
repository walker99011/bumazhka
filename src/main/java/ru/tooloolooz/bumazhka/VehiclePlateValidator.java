package ru.tooloolooz.bumazhka;

import ru.tooloolooz.bumazhka.plate.AbstractPlateValidator;
import ru.tooloolooz.bumazhka.plate.VehiclePlateType;
import ru.tooloolooz.bumazhka.plate.impl.Type1VehiclePlateAbstractPlateValidator;

/**
 * Utility class for validating Russian Federation vehicle registration plates (license plates).
 * <p>
 * This class provides validation for state registration plates according to Russian GOST R 50577-2018 standard.
 * It supports next formats:
 * <ul>
 *   <li><b>Type 1/1A</b></li>
 * </ul>
 * The class provides both automatic plate type detection and explicit type-based validation.
 * <p>
 * Invalid plates cause {@link NotValidException} to be thrown from {@link #validate(String)}
 * and {@link #validate(String, VehiclePlateType)}.
 * <p>
 * Implementation uses separate validator instances for each plate type,
 * ensuring compliance with formal GOST requirements.
 *
 * @see VehiclePlateType
 * @see NotValidException
 * @see AbstractPlateValidator
 * @see <a href="https://docs.cntd.ru/document/1200160380">GOST R 50577-2018 State Registration Plates for Vehicles</a>
 */
public final class VehiclePlateValidator {
    /**
     * Validator instance for type 1 vehicle registration plates.
     */
    private static final Type1VehiclePlateAbstractPlateValidator TYPE_1_VEHICLE_PLATE_VALIDATOR =
            Type1VehiclePlateAbstractPlateValidator.INSTANCE;

    /**
     * Base error message used in exceptions for invalid plates.
     */
    private static final String EXCEPTION_MESSAGE = "Invalid vehicle state registration plate: ";

    /**
     * This class is a utility class and should not be instantiated.
     *
     * @throws UnsupportedOperationException always.
     */
    private VehiclePlateValidator() {
        Assert.unsupported("Utility class should not be instantiated");
    }

    /**
     * Validates any vehicle registration plate.
     * <p>
     * This is the exception-throwing variant of {@link #isValid(String)}.
     *
     * @param plate the registration plate string to validate.
     * @throws NotValidException if {@code plate} is null or invalid.
     * @see #isValid(String)
     */
    public static void validate(final String plate) {
        if (!isValid(plate)) {
            throw new NotValidException(EXCEPTION_MESSAGE + plate);
        }
    }

    /**
     * Validates a vehicle registration plate against a specific type.
     * <p>
     * This is the exception-throwing variant of {@link #isValid(String, VehiclePlateType)}.
     *
     * @param plate the registration plate string to validate.
     * @param type  the required {@link VehiclePlateType} format.
     * @throws NotValidException if {@code plate} is null or invalid.
     * @see #isValid(String, VehiclePlateType)
     */
    public static void validate(final String plate, final VehiclePlateType type) {
        if (!isValid(plate, type)) {
            throw new NotValidException(EXCEPTION_MESSAGE + plate);
        }
    }

    /**
     * Validates any vehicle registration plate.
     * <p>
     * This method checks the plate against all supported formats.
     * The validation includes:
     * <ul>
     *   <li>Null check.</li>
     *   <li>Length check.</li>
     *   <li>Character pattern validation.</li>
     * </ul>
     *
     * @param plate the registration plate string to validate
     * @return {@code true} if the plate is valid, {@code false} otherwise
     */
    public static boolean isValid(final String plate) {
        return TYPE_1_VEHICLE_PLATE_VALIDATOR.isValid(plate);
    }

    /**
     * Validates a vehicle registration plate against a specific type.
     * <p>
     * This method checks if the plate conforms to the specified format type only.
     * Use this method when you know the expected plate type.
     * The validation includes:
     * <ul>
     *   <li>Null check.</li>
     *   <li>Length check.</li>
     *   <li>Character pattern validation.</li>
     * </ul>
     *
     * @param plate the registration plate string to validate.
     * @param type  vehicle state registration plate {@link VehiclePlateType type}.
     * @return {@code true} if the plate is valid, {@code false} otherwise.
     */
    public static boolean isValid(final String plate, final VehiclePlateType type) {
        return getValidator(type).isValid(plate);
    }

    /**
     * Returns the appropriate validator instance for the specified plate type.
     * <p>
     * This internal method provides the strategy pattern implementation for plate validation.
     *
     * @param type the vehicle plate type
     * @return the validator instance for the specified type
     */
    private static AbstractPlateValidator getValidator(final VehiclePlateType type) {
        return switch (type) {
            case TYPE_1, TYPE_1A -> TYPE_1_VEHICLE_PLATE_VALIDATOR;
        };
    }
}

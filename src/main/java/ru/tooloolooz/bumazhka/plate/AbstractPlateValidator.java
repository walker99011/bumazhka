package ru.tooloolooz.bumazhka.plate;

/**
 * Abstract base class for vehicle registration plate validators.
 * <p>
 * This class defines the common interface for all plate validator implementations.
 * Each concrete validator is responsible for validating a specific
 * {@link VehiclePlateType} of Russian Federation vehicle registration plates.
 *
 * @see VehiclePlateType
 */
public interface AbstractPlateValidator {
    /**
     * Validates a vehicle registration plate according to the specific format rules.
     * <p>
     * This method checks if the plate conforms to the specified format type only.
     *
     * @param plate the registration plate string to validate.
     * @return {@code true} if the plate is valid, {@code false} otherwise.
     */
    boolean isValid(String plate);

    /**
     * Returns the vehicle plate type which this validator is designed to validate.
     * <p>
     * This method allows clients to determine which specific plate format this
     * validator supports. It enables type-safe validation and proper routing
     * in validation pipelines.
     *
     * @return {@link VehiclePlateType} which this validator instance supports.
     */
    VehiclePlateType getType();
}

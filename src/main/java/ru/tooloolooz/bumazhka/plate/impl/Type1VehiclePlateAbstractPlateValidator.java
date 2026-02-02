package ru.tooloolooz.bumazhka.plate.impl;

import ru.tooloolooz.bumazhka.Assert;
import ru.tooloolooz.bumazhka.VehicleRegionCodeValidator;
import ru.tooloolooz.bumazhka.plate.AbstractPlateValidator;
import ru.tooloolooz.bumazhka.plate.VehiclePlateType;

/**
 * Concrete implementation of a validator for Type 1 vehicle registration plates.
 * <p>
 * This utility class provides validation for Russian Federation vehicle state registration plates.
 * <p>
 * <b>Allowed formats:</b> {@code М000ММ55} or {@code М000ММ555}.
 * <ul>
 *   <li>0 - digit indicating the number.</li>
 *   <li>M - letter indicating the series.</li>
 *   <li>5 - digit of the region code.</li>
 * </ul>
 * <b>Validation Scope:</b>
 * This validator checks:
 * <ul>
 *   <li>String length (must be 8 or 9 characters)</li>
 *   <li>Character types at each position</li>
 *   <li>Allowed Cyrillic letter set for series positions</li>
 *   <li>Region code validity using {@link VehicleRegionCodeValidator}</li>
 * </ul>
 * <b>Thread Safety:</b>
 * This class is thread-safe as it contains no mutable state and all
 * validation logic uses only method parameters and immutable constants.
 *
 * @see AbstractPlateValidator
 * @see VehiclePlateType#TYPE_1
 * @see <a href="https://docs.cntd.ru/document/1200160380">GOST R 50577-2018 State Registration Plates for Vehicles</a>
 */
public final class Type1VehiclePlateAbstractPlateValidator implements AbstractPlateValidator {
    /**
     * Singleton instance of the Type 1 vehicle plate validator.
     * <p>
     * Use this instance for all Type 1 plate validations to ensure consistency
     * and avoid unnecessary object instantiation.
     */
    public static final Type1VehiclePlateAbstractPlateValidator INSTANCE =
            new Type1VehiclePlateAbstractPlateValidator();

    /**
     * Maximum allowed plate length (9 characters).
     * <p>
     * This includes plates with 3-digit region codes.
     */
    private static final int MAX_PLATE_SIZE = 9;

    /**
     * Minimum allowed plate length (8 characters).
     * <p>
     * This includes plates with 2-digit region codes.
     */
    private static final int MIN_PLATE_SIZE = 8;

    /**
     * Position constant for the first character (series letter).
     */
    private static final int POSITION_0 = 0;

    /**
     * Position constant for the second character (first digit of registration number).
     */
    private static final int POSITION_1 = 1;

    /**
     * Position constant for the third character (second digit of registration number).
     */
    private static final int POSITION_2 = 2;

    /**
     * Position constant for the fourth character (third digit of registration number).
     */
    private static final int POSITION_3 = 3;

    /**
     * Position constant for the fifth character (first letter of second series part).
     */
    private static final int POSITION_4 = 4;

    /**
     * Position constant for the sixth character (second letter of second series part).
     */
    private static final int POSITION_5 = 5;

    /**
     * Position constant for the seventh character (first digit of region code).
     */
    private static final int POSITION_6 = 6;

    /**
     * This class is a utility class and should not be instantiated.
     *
     * @throws UnsupportedOperationException always.
     */
    private Type1VehiclePlateAbstractPlateValidator() {
        Assert.unsupported("Utility class should not be instantiated");
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Allowed Letters (12 total):</b> 'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'
     * <p>
     */
    @Override
    public boolean isValid(final String plate) {
        Assert.notNull(plate, "Plate must be not empty");
        final int plateLength = plate.length();
        if (plateLength < MIN_PLATE_SIZE || MAX_PLATE_SIZE < plateLength) {
            return false;
        }
        return isAllowedLetter(plate.charAt(POSITION_0))
               && isDigit(plate.charAt(POSITION_1))
               && isDigit(plate.charAt(POSITION_2))
               && isDigit(plate.charAt(POSITION_3))
               && isAllowedLetter(plate.charAt(POSITION_4))
               && isAllowedLetter(plate.charAt(POSITION_5))
               && VehicleRegionCodeValidator.isValid(plate.substring(POSITION_6, plateLength));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VehiclePlateType getType() {
        return VehiclePlateType.TYPE_1;
    }

    /**
     * Checks if a character is an allowed Cyrillic letter for vehicle plate series.
     * <p>
     * Russian vehicle registration plates permit only 12 specific Cyrillic letters in the series positions.
     * <b>Allowed Letters (12 total):</b> 'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'
     *
     * @param character the character to check for allowance in plate series
     * @return {@code true} if the character is one of the 12 allowed Cyrillic letters,
     * {@code false} otherwise (including Latin letters, other Cyrillic letters,
     * digits, symbols, etc.)
     */
    private static boolean isAllowedLetter(final char character) {
        return switch (character) {
            case 'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х' -> true;
            default -> false;
        };
    }

    /**
     * Checks if a character is a decimal digit (0-9).
     *
     * @param character the character to check for digit status
     * @return {@code true} if the character is a basic digit (0-9), {@code false} otherwise
     */
    private static boolean isDigit(final char character) {
        return '0' <= character && character <= '9';
    }
}

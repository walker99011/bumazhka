package ru.tooloolooz.bumazhka.plate.impl;

import ru.tooloolooz.bumazhka.Assert;
import ru.tooloolooz.bumazhka.plate.AbstractPlateValidator;
import ru.tooloolooz.bumazhka.plate.VehiclePlateType;

/**
 * Validator for 1 type of vehicle state registration plates.
 * This utility class provides validation for Russian Federation vehicle state registration plates.
 * Allowed formats: М000ММ55 or М000ММ555.
 * 0 - digit indicating the number
 * M - letter indicating the series
 * 5 - digit of the region code
 *
 * @see <a href="https://docs.cntd.ru/document/1200160380"/>
 */
public class Type1VehiclePlateAbstractPlateValidator extends AbstractPlateValidator {
    /**
     * Instance of validator. Used to
     */
    public static final Type1VehiclePlateAbstractPlateValidator INSTANCE = new Type1VehiclePlateAbstractPlateValidator();
    private final static int MAX_PLATE_SIZE = 9;
    private final static int MIN_PLATE_SIZE = 8;
    private final static int POSITION_0 = 0;
    private final static int POSITION_1 = 1;
    private final static int POSITION_2 = 2;
    private final static int POSITION_3 = 3;
    private final static int POSITION_4 = 4;
    private final static int POSITION_5 = 5;

    @Override
    public boolean isValid(String plate) {
        Assert.notNull(plate, "Plate must be not empty");
        final int plateLength = plate.length();
        if (plateLength < MIN_PLATE_SIZE || MAX_PLATE_SIZE < plateLength) {
            return false;
        }
        if (isForbiddenLetter(plate.charAt(POSITION_0))) {
            return false;
        }
        if (isForbiddenDigit(plate.charAt(POSITION_1))
            || isForbiddenDigit(plate.charAt(POSITION_2))
            || isForbiddenDigit(plate.charAt(POSITION_3))) {
            return false;
        }
        if (isForbiddenLetter(plate.charAt(POSITION_4)) || isForbiddenLetter(plate.charAt(POSITION_5))) {
            return false;
        }
//        if (!VehicleRussianRegionCodeValidator.isValid(plate.substring(POSITION_6, plateLength))) {
//            return false;
//        }
        return true;
    }

    @Override
    public VehiclePlateType getType() {
        return VehiclePlateType.TYPE_1;
    }

    private static boolean isForbiddenLetter(char character) {
        return switch (character) {
            case 'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х' -> false;
            default -> true;
        };
    }

    private static boolean isForbiddenDigit(char character) {
        return '0' <= character && character <= '9';
    }
}

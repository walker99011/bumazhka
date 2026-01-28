package ru.ivamly.bumazhka.plate;


/**
 * Validator for 1 group of vehicle state registration plates.
 * Allowed formats: М000ММ55 or М000ММ555.
 * 0 - digit indicating the number
 * M - letter indicating the series
 * 5 - digit of the region code
 */
public class Type1VehiclePlateValidator implements VehiclePlateValidator {
    private final static String EXCEPTION_MESSAGE = "Invalid vehicle state registration plate: ";
    private final static int MAX_PLATE_SIZE = 9;
    private final static int MIN_PLATE_SIZE = 8;
    private final static int POSITION_0 = 0;
    private final static int POSITION_1 = 1;
    private final static int POSITION_2 = 2;
    private final static int POSITION_3 = 3;
    private final static int POSITION_4 = 4;
    private final static int POSITION_5 = 5;

    @Override
    public void validate(String plate) throws NotValidException {
        if (!isValid(plate)) {
            throw new NotValidException(EXCEPTION_MESSAGE + plate);
        }
    }

    @Override
    public boolean isValid(String plate) {
        Assert.notEmpty(plate, "Plate must be not empty");
        var plateLength = plate.length();
        if (plateLength < MIN_PLATE_SIZE || plateLength > MAX_PLATE_SIZE) {
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

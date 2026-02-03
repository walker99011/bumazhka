package ru.tooloolooz.bumazhka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.tooloolooz.bumazhka.plate.VehiclePlateType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VehiclePlateValidatorTest {

    /**
     * Test data provider for licence plates type 1/1A.
     * Provides plates with their expected validation results.
     *
     * @return Stream of Arguments where:
     * - First argument (String): plate
     * - Second argument (Boolean): expected validation result (true = valid, false = invalid)
     */
    private static Stream<Arguments> validLicencePlates() {
        return Stream.of(
                Arguments.of("А019АА61"),
                Arguments.of("А019АА161")
        );
    }

    private static Stream<Arguments> invalidLicencePlates() {
        return Stream.of(
                Arguments.of("а019АА161"),
                Arguments.of("Аа19АА161"),
                Arguments.of("А0а9АА161"),
                Arguments.of("А01аАА161"),
                Arguments.of("А019аА161"),
                Arguments.of("А019Аа161"),
                Arguments.of("А019ААа61"),
                Arguments.of("А019АА1а1"),
                Arguments.of("А019АА16а"),
                Arguments.of("AА019АА161"),
                Arguments.of("А019АА161A"),
                Arguments.of("")
        );
    }


    @Test
    void constructorTest() throws NoSuchMethodException {
        Constructor<VehiclePlateValidator> constructor = VehiclePlateValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThat(Modifier.isPrivate(constructor.getModifiers()))
                .isTrue();
        assertThat(constructor.getParameterCount())
                .isZero();
        assertThatThrownBy(constructor::newInstance)
                .isInstanceOf(InvocationTargetException.class)
                .extracting(Throwable::getCause)
                .isInstanceOf(UnsupportedOperationException.class)
                .extracting(Throwable::getMessage)
                .isEqualTo("Utility class should not be instantiated");
    }

    @ParameterizedTest
    @MethodSource("validLicencePlates")
    void validateTestForValidLicensePlates(String code) {
        assertThatCode(() -> VehiclePlateValidator.validate(code))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("validLicencePlates")
    void validateTestForValidLicensePlatesType1(String code) {
        assertThatCode(() -> VehiclePlateValidator.validate(code, VehiclePlateType.TYPE_1))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("validLicencePlates")
    void validateTestForValidLicensePlatesType1A(String code) {
        assertThatCode(() -> VehiclePlateValidator.validate(code, VehiclePlateType.TYPE_1A))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("invalidLicencePlates")
    void validateTestForInvalidLicensePlates(String code) {
        assertThatThrownBy(() -> VehiclePlateValidator.validate(code))
                .isInstanceOf(NotValidException.class)
                .extracting(Throwable::getMessage)
                .isEqualTo("Invalid vehicle state registration plate: " + code);
    }

    @ParameterizedTest
    @MethodSource("invalidLicencePlates")
    void validateTestForInvalidLicensePlatesForType1(String code) {
        assertThatThrownBy(() -> VehiclePlateValidator.validate(code, VehiclePlateType.TYPE_1))
                .isInstanceOf(NotValidException.class)
                .extracting(Throwable::getMessage)
                .isEqualTo("Invalid vehicle state registration plate: " + code);
    }

    @Test
    @SuppressWarnings("NullAway")
    void validateTestForNullCode() {
        assertThatThrownBy(() -> VehiclePlateValidator.validate(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Plate must be not null");
    }

    @Test
    @SuppressWarnings("NullAway")
    void validateTestForNullCodeType1() {
        assertThatThrownBy(() -> VehiclePlateValidator.validate(null, VehiclePlateType.TYPE_1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Plate must be not null");
    }

    @Test
    @SuppressWarnings("NullAway")
    void validateTestForNullType() {
        assertThatThrownBy(() -> VehiclePlateValidator.validate("", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Type must be not null");
    }
}

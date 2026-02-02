package ru.tooloolooz.bumazhka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.tooloolooz.bumazhka.plate.VehiclePlateType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
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
    private static Stream<Arguments> licencePlates() {
        return Stream.of(
                Arguments.of("A111AA61", true),
                Arguments.of("A111AA161", true),
                Arguments.of("a111AA161", false),
                Arguments.of("Aa11AA161", false),
                Arguments.of("A1a1AA161", false),
                Arguments.of("A11aAA161", false),
                Arguments.of("A111aA161", false),
                Arguments.of("A111Aa161", false),
                Arguments.of("A111AAa61", false),
                Arguments.of("A111AA1a1", false),
                Arguments.of("A111AA16a", false)
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
    @MethodSource("licencePlates")
    void isValidTestForAnyPlateType(String code, boolean result) {
        assertThat(VehiclePlateValidator.isValid(code))
                .isEqualTo(result);
    }

    @ParameterizedTest
    @MethodSource("licencePlates")
    void isValidTestForType1(String code, boolean result) {
        assertThat(VehiclePlateValidator.isValid(code, VehiclePlateType.TYPE_1))
                .isEqualTo(result);
    }

    @ParameterizedTest
    @MethodSource("licencePlates")
    void isValidTestForType1A(String code, boolean result) {
        assertThat(VehiclePlateValidator.isValid(code, VehiclePlateType.TYPE_1A))
                .isEqualTo(result);
    }
}

package ru.tooloolooz.bumazhka.plate;

/**
 * Enumeration representing different types of Russian Federation vehicle registration plates.
 * <p>
 * Each type corresponds to a specific format and use case defined by GOST R 50577-2018.
 * The types differ in their structural format and allowed characters or digits.
 *
 * @see <a href="https://docs.cntd.ru/document/1200160380">GOST R 50577-2018 State Registration Plates for Vehicles</a>
 */
public enum VehiclePlateType {
    /**
     * 1 type of vehicle registration plate.
     * <p>
     * Format: {@code М000ММ55} or {@code М000ММ555}
     * Where:
     * <ul>
     *   <li>0 - digit indicating the number.</li>
     *   <li>M - letter indicating the series.</li>
     *   <li>5 - digit of the region code.</li>
     * </ul>
     */
    TYPE_1,

    /**
     * 1A type of vehicle registration plate. The type format has no differences from the {@link #TYPE_1}.
     * <p>
     * Is used by vehicles with a non-standard attachment point.
     */
    TYPE_1A
}

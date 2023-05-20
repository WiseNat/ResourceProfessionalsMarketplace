package com.wise.resource.professionals.marketplace.constant;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Bandings which are directly mapped in {@link com.wise.resource.professionals.marketplace.entity.BandingEntity}.
 */
public enum BandingEnum {
    BAND_ONE("B1"),
    BAND_TWO("B2"),
    BAND_THREE("B3"),
    BAND_FOUR("B4"),
    BAND_FIVE("B5");

    private static final LinkedHashMap<String, BandingEnum> cache = new LinkedHashMap<>();

    static {
        for (BandingEnum e : values()) {
            cache.put(e.value, e);
        }
    }

    public final String value;

    BandingEnum(String value) {
        this.value = value;
    }

    /**
     * Converts a given value to the enum value that it is associated with.
     *
     * @param value the value to be found.
     * @return the enum value for the given value.
     */
    public static BandingEnum valueToEnum(String value) {
        return cache.get(value);
    }

    /**
     * Gets all the values associated with each enum value.
     *
     * @return a set of values.
     */
    public static Set<String> getAllValues() {
        return cache.keySet();
    }
}

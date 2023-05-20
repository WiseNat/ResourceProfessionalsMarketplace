package com.wise.resource.professionals.marketplace.constant;

import java.util.LinkedHashMap;
import java.util.Set;

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

    public static BandingEnum valueToEnum(String label) {
        return cache.get(label);
    }

    public static Set<String> getAllValues() {
        return cache.keySet();
    }
}

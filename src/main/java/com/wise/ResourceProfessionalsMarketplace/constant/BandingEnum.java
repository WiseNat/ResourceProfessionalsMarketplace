package com.wise.ResourceProfessionalsMarketplace.constant;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;

public enum BandingEnum {
    BandOne("B1"),
    BandTwo("B2"),
    BandThree("B3"),
    BandFour("B4"),
    BandFive("B5"),
    BandSix("B6"),
    BandSeven("B7"),
    BandEight("B8"),
    BandNine("B9"),
    BandTen("B10");

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

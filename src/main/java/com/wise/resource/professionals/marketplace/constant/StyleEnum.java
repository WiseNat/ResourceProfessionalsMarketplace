package com.wise.resource.professionals.marketplace.constant;

import java.util.LinkedHashMap;
import java.util.Set;

public enum StyleEnum {
    NavbarButtonActive("navbar-button-active"),
    NegativeControl("negative-control"),
    NegativeLabel("negative-label"),
    NegativeDatePickerDayCell("negative-date-picker-day-cell");

    private static final LinkedHashMap<String, StyleEnum> cache = new LinkedHashMap<>();

    static {
        for (StyleEnum e : values()) {
            cache.put(e.value, e);
        }
    }

    public final String value;

    StyleEnum(String value) {
        this.value = value;
    }

    public static StyleEnum valueToEnum(String label) {
        return cache.get(label);
    }

    public static Set<String> getAllValues() {
        return cache.keySet();
    }
}

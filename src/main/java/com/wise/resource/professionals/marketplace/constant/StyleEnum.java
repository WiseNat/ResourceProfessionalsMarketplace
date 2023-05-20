package com.wise.resource.professionals.marketplace.constant;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * The names of various style classes which exist within the stylesheet at {@link ApplicationEnum#DEFAULT_STYLESHEET_PATH}
 */
public enum StyleEnum {
    NAVBAR_BUTTON_ACTIVE("navbar-button-active"),
    NEGATIVE_CONTROL("negative-control"),
    NEGATIVE_LABEL("negative-label"),
    NEGATIVE_DATE_PICKER_DAY_CELL("negative-date-picker-day-cell");

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

    /**
     * Converts a given value to the enum value that it is associated with.
     *
     * @param value the value to be found.
     * @return the enum value for the given value.
     */
    public static StyleEnum valueToEnum(String value) {
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

package com.wise.resource.professionals.marketplace.constant;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;

/**
 * Application specific values which can't be placed inside of application.properties
 */
public enum ApplicationEnum {
    DEFAULT_STYLESHEET_PATH(Objects.requireNonNull(ApplicationEnum.class.getResource("../styles/application.css")).toExternalForm());

    private static final LinkedHashMap<String, ApplicationEnum> cache = new LinkedHashMap<>();

    static {
        for (ApplicationEnum e : values()) {
            cache.put(e.value, e);
        }
    }

    public final String value;

    ApplicationEnum(String value) {
        this.value = value;
    }

    /**
     * Converts a given value to the enum value that it is associated with.
     *
     * @param value the value to be found.
     * @return the enum value for the given value.
     */
    public static ApplicationEnum valueToEnum(String value) {
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

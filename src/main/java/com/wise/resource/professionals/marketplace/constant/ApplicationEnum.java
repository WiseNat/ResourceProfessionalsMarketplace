package com.wise.resource.professionals.marketplace.constant;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;

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

    public static ApplicationEnum valueToEnum(String label) {
        return cache.get(label);
    }

    public static Set<String> getAllValues() {
        return cache.keySet();
    }
}

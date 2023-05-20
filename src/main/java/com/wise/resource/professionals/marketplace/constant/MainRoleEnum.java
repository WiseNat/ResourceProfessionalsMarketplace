package com.wise.resource.professionals.marketplace.constant;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Main roles which are directly mapped in {@link com.wise.resource.professionals.marketplace.entity.MainRoleEntity}.
 */
public enum MainRoleEnum {
    DEVELOPER("Developer"),
    DEV_OPS("DevOps"),
    ARCHITECT("Architect"),
    UX_DESIGNER("UX Designer"),
    TESTER("Tester"),
    BUSINESS_ANALYST("Business Analyst"),
    SCRUM_MASTER("Scrum Master");

    private static final LinkedHashMap<String, MainRoleEnum> cache = new LinkedHashMap<>();

    static {
        for (MainRoleEnum e : values()) {
            cache.put(e.value, e);
        }
    }

    public final String value;

    MainRoleEnum(String value) {
        this.value = value;
    }

    /**
     * Converts a given value to the enum value that it is associated with.
     *
     * @param value the value to be found.
     * @return the enum value for the given value.
     */
    public static MainRoleEnum valueToEnum(String value) {
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

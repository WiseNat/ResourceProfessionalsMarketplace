package com.wise.resource.professionals.marketplace.constant;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Sub roles which are directly mapped in {@link com.wise.resource.professionals.marketplace.entity.SubRoleEntity}.
 */
public enum SubRoleEnum {
    WEB_DEVELOPER("Web Developer"),
    FRONTEND_DEVELOPER("Frontend Developer"),
    BACKEND_DEVELOPER("Backend Developer"),
    FULL_STACK_DEVELOPER("Full Stack Developer"),
    MOBILE_DEVELOPER("Mobile Developer"),
    SOFTWARE_DEVELOPER("Software Developer"),
    GAME_DEVELOPER("Game Developer"),
    SYSTEM_ARCHITECT("System Architect"),
    SOLUTION_ARCHITECT("Solution Architect"),
    SECURITY_ARCHITECT("Security Architect"),
    DATA_ARCHITECT("Data Architect"),
    TEST_ANALYST("Test Analyst"),
    AUTOMATION_TESTER("Automation Tester"),
    MANUAL_TESTER("Manual Tester"),
    PERFORMANCE_TESTER("Performance Tester");

    private static final LinkedHashMap<String, SubRoleEnum> cache = new LinkedHashMap<>();

    static {
        for (SubRoleEnum e : values()) {
            cache.put(e.value, e);
        }
    }

    public final String value;

    SubRoleEnum(String value) {
        this.value = value;
    }

    /**
     * Converts a given value to the enum value that it is associated with.
     *
     * @param value the value to be found.
     * @return the enum value for the given value.
     */
    public static SubRoleEnum valueToEnum(String value) {
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

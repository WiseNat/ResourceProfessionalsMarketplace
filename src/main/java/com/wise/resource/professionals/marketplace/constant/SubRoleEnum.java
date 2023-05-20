package com.wise.resource.professionals.marketplace.constant;

import java.util.LinkedHashMap;
import java.util.Set;

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

    public static SubRoleEnum valueToEnum(String label) {
        return cache.get(label);
    }

    public static Set<String> getAllValues() {
        return cache.keySet();
    }
}

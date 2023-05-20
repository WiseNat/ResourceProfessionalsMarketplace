package com.wise.resource.professionals.marketplace.constant;

import java.util.LinkedHashMap;
import java.util.Set;

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

    public static MainRoleEnum valueToEnum(String label) {
        return cache.get(label);
    }

    public static Set<String> getAllValues() {
        return cache.keySet();
    }
}

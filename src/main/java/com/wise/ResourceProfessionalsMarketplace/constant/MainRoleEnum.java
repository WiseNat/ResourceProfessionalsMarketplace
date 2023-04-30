package com.wise.ResourceProfessionalsMarketplace.constant;

import java.util.LinkedHashMap;
import java.util.Set;

public enum MainRoleEnum {
    Developer("Developer"),
    DevOps("DevOps"),
    Architect("Architect"),
    UXDesigner("UX Designer"),
    Tester("Tester"),
    BusinessAnalyst("Business Analyst"),
    ScrumMaster("Scrum Master");

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

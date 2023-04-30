package com.wise.ResourceProfessionalsMarketplace.constant;

import java.util.LinkedHashMap;
import java.util.Set;

public enum AccountTypeEnum {
    Admin("Admin"),
    Resource("Resource"),
    ProjectManager("Project Manager");

    private static final LinkedHashMap<String, AccountTypeEnum> cache = new LinkedHashMap<>();

    static {
        for (AccountTypeEnum e : values()) {
            cache.put(e.value, e);
        }
    }

    public final String value;

    AccountTypeEnum(String value) {
        this.value = value;
    }

    public static AccountTypeEnum valueToEnum(String label) {
        return cache.get(label);
    }

    public static Set<String> getAllValues() {
        return cache.keySet();
    }
}

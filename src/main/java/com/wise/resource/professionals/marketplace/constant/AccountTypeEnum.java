package com.wise.resource.professionals.marketplace.constant;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Account Types which are directly mapped in {@link com.wise.resource.professionals.marketplace.entity.AccountTypeEntity}.
 */
public enum AccountTypeEnum {
    ADMIN("Admin"),
    RESOURCE("Resource"),
    PROJECT_MANAGER("Project Manager");

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

    /**
     * Converts a given value to the enum value that it is associated with.
     *
     * @param value the value to be found.
     * @return the enum value for the given value.
     */
    public static AccountTypeEnum valueToEnum(String value) {
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

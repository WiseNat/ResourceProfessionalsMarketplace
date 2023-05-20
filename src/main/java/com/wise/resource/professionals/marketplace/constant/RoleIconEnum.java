package com.wise.resource.professionals.marketplace.constant;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;

/**
 * URLs to images associated with each main role and sub role.
 * @see MainRoleEnum
 * @see SubRoleEnum
 */
public enum RoleIconEnum {
    DEVELOPER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/developer.png"))),
    WEB_DEVELOPER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/web_developer.png"))),
    FRONTEND_DEVELOPER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/frontend_developer.png"))),
    BACKEND_DEVELOPER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/backend_developer.png"))),
    FULL_STACK_DEVELOPER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/full_stack_developer.png"))),
    MOBILE_DEVELOPER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/mobile_developer.png"))),
    SOFTWARE_DEVELOPER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/software_developer.png"))),
    GAME_DEVELOPER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/game_developer.png"))),
    DEV_OPS(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/devops.png"))),
    ARCHITECT(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/architect.png"))),
    SYSTEM_ARCHITECT(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/system_architect.png"))),
    SOLUTION_ARCHITECT(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/solution_architect.png"))),
    SECURITY_ARCHITECT(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/security_architect.png"))),
    DATA_ARCHITECT(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/data_architect.png"))),
    UX_DESIGNER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/ux_designer.png"))),
    TESTER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/tester.png"))),
    TEST_ANALYST(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/test_analyst.png"))),
    AUTOMATION_TESTER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/automation_tester.png"))),
    MANUAL_TESTER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/manual_tester.png"))),
    PERFORMANCE_TESTER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/performance_tester.png"))),
    BUSINESS_ANALYST(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/business_analyst.png"))),
    SCRUM_MASTER(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/scrum_master.png")));

    private static final LinkedHashMap<URL, RoleIconEnum> cache = new LinkedHashMap<>();

    static {
        for (RoleIconEnum e : values()) {
            cache.put(e.value, e);
        }
    }

    public final URL value;

    RoleIconEnum(URL value) {
        this.value = value;
    }

    /**
     * Converts a given value to the enum value that it is associated with.
     *
     * @param value the value to be found.
     * @return the enum value for the given value.
     */
    public static RoleIconEnum valueToEnum(URL value) {
        return cache.get(value);
    }

    /**
     * Gets all the values associated with each enum value.
     *
     * @return a set of values.
     */
    public static Set<URL> getAllValues() {
        return cache.keySet();
    }
}

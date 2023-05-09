package com.wise.resource.professionals.marketplace.constant;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;

public enum RoleIconEnum {
    Developer(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/developer.png"))),
    WebDeveloper(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/web_developer.png"))),
    FrontendDeveloper(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/frontend_developer.png"))),
    BackendDeveloper(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/backend_developer.png"))),
    FullStackDeveloper(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/full_stack_developer.png"))),
    MobileDeveloper(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/mobile_developer.png"))),
    SoftwareDeveloper(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/software_developer.png"))),
    GameDeveloper(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/game_developer.png"))),
    DevOps(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/devops.png"))),
    Architect(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/architect.png"))),
    SystemArchitect(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/system_architect.png"))),
    SolutionArchitect(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/solution_architect.png"))),
    SecurityArchitect(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/security_architect.png"))),
    DataArchitect(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/data_architect.png"))),
    UXDesigner(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/ux_designer.png"))),
    Tester(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/tester.png"))),
    TestAnalyst(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/test_analyst.png"))),
    AutomationTester(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/automation_tester.png"))),
    ManualTester(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/manual_tester.png"))),
    PerformanceTester(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/performance_tester.png"))),
    BusinessAnalyst(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/business_analyst.png"))),
    ScrumMaster(Objects.requireNonNull(RoleIconEnum.class.getResource("../images/scrum_master.png")));

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

    public static RoleIconEnum valueToEnum(URL value) {
        return cache.get(value);
    }

    public static Set<URL> getAllValues() {
        return cache.keySet();
    }
}

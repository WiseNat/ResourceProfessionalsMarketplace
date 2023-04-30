package com.wise.ResourceProfessionalsMarketplace.constant;

import java.util.LinkedHashMap;
import java.util.Set;

public enum SubRoleEnum {
    WebDeveloper("Web Developer"),
    FrontendDeveloper("Frontend Developer"),
    BackendDeveloper("Backend Developer"),
    FullStackDeveloper("Full Stack Developer"),
    MobileDeveloper("Mobile Developer"),
    SoftwareDeveloper("Software Developer"),
    GameDeveloper("Game Developer"),
    SystemArchitect("System Architect"),
    SolutionArchitect("Solution Architect"),
    SecurityArchitect("Security Architect"),
    DataArchitect("Data Architect"),
    TestAnalyst("Test Analyst"),
    AutomationTester("Automation Tester"),
    ManualTester("Manual Tester"),
    PerformanceTester("Performance Tester");

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

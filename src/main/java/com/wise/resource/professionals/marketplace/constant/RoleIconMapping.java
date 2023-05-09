package com.wise.resource.professionals.marketplace.constant;

import java.util.HashMap;

public class RoleIconMapping {
    public static final HashMap<MainRoleEnum, RoleIconEnum> MAIN_ROLE_ICON_MAPPING = new HashMap<MainRoleEnum, RoleIconEnum>() {{
        put(MainRoleEnum.Developer, RoleIconEnum.Developer);
        put(MainRoleEnum.DevOps, RoleIconEnum.DevOps);
        put(MainRoleEnum.Architect, RoleIconEnum.Architect);
        put(MainRoleEnum.UXDesigner, RoleIconEnum.UXDesigner);
        put(MainRoleEnum.Tester, RoleIconEnum.Tester);
        put(MainRoleEnum.BusinessAnalyst, RoleIconEnum.BusinessAnalyst);
        put(MainRoleEnum.ScrumMaster, RoleIconEnum.ScrumMaster);
    }};

    public static final HashMap<SubRoleEnum, RoleIconEnum> SUB_ROLE_ICON_MAPPING = new HashMap<SubRoleEnum, RoleIconEnum>() {{
        put(SubRoleEnum.WebDeveloper, RoleIconEnum.WebDeveloper);
        put(SubRoleEnum.FrontendDeveloper, RoleIconEnum.FrontendDeveloper);
        put(SubRoleEnum.BackendDeveloper, RoleIconEnum.BackendDeveloper);
        put(SubRoleEnum.FullStackDeveloper, RoleIconEnum.FullStackDeveloper);
        put(SubRoleEnum.MobileDeveloper, RoleIconEnum.MobileDeveloper);
        put(SubRoleEnum.SoftwareDeveloper, RoleIconEnum.SoftwareDeveloper);
        put(SubRoleEnum.GameDeveloper, RoleIconEnum.GameDeveloper);
        put(SubRoleEnum.SystemArchitect, RoleIconEnum.SystemArchitect);
        put(SubRoleEnum.SolutionArchitect, RoleIconEnum.SolutionArchitect);
        put(SubRoleEnum.SecurityArchitect, RoleIconEnum.SecurityArchitect);
        put(SubRoleEnum.DataArchitect, RoleIconEnum.DataArchitect);
        put(SubRoleEnum.TestAnalyst, RoleIconEnum.TestAnalyst);
        put(SubRoleEnum.AutomationTester, RoleIconEnum.AutomationTester);
        put(SubRoleEnum.ManualTester, RoleIconEnum.ManualTester);
        put(SubRoleEnum.PerformanceTester, RoleIconEnum.PerformanceTester);
    }};
}

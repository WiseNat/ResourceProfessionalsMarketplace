package com.wise.ResourceProfessionalsMarketplace.constant;

import java.util.HashMap;

public class RoleMapping {
    public static final HashMap<MainRoleEnum, SubRoleEnum[]> ROLE_MAPPING = new HashMap<MainRoleEnum, SubRoleEnum[]>() {{
        put(MainRoleEnum.Developer, new SubRoleEnum[]{
                SubRoleEnum.WebDeveloper,
                SubRoleEnum.FrontendDeveloper,
                SubRoleEnum.BackendDeveloper,
                SubRoleEnum.FullStackDeveloper,
                SubRoleEnum.MobileDeveloper,
                SubRoleEnum.SoftwareDeveloper,
                SubRoleEnum.GameDeveloper,
        });
        put(MainRoleEnum.DevOps, new SubRoleEnum[0]);
        put(MainRoleEnum.Architect, new SubRoleEnum[]{
                SubRoleEnum.SystemArchitect,
                SubRoleEnum.SolutionArchitect,
                SubRoleEnum.SecurityArchitect,
                SubRoleEnum.DataArchitect,
        });
        put(MainRoleEnum.UXDesigner, new SubRoleEnum[0]);
        put(MainRoleEnum.Tester, new SubRoleEnum[]{
                SubRoleEnum.TestAnalyst,
                SubRoleEnum.AutomationTester,
                SubRoleEnum.ManualTester,
                SubRoleEnum.PerformanceTester,
        });
        put(MainRoleEnum.BusinessAnalyst, new SubRoleEnum[0]);
        put(MainRoleEnum.ScrumMaster, new SubRoleEnum[0]);
    }};
}

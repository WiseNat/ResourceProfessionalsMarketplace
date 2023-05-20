package com.wise.resource.professionals.marketplace.constant;

import javafx.scene.image.Image;

import java.util.HashMap;

public class RoleIconMapping {
    public static final HashMap<MainRoleEnum, Image> MAIN_ROLE_ICON_MAPPING = new HashMap<MainRoleEnum, Image>() {{
        put(MainRoleEnum.Developer, new Image(RoleIconEnum.Developer.value.toString()));
        put(MainRoleEnum.DevOps, new Image(RoleIconEnum.DevOps.value.toString()));
        put(MainRoleEnum.Architect, new Image(RoleIconEnum.Architect.value.toString()));
        put(MainRoleEnum.UXDesigner, new Image(RoleIconEnum.UXDesigner.value.toString()));
        put(MainRoleEnum.Tester, new Image(RoleIconEnum.Tester.value.toString()));
        put(MainRoleEnum.BusinessAnalyst, new Image(RoleIconEnum.BusinessAnalyst.value.toString()));
        put(MainRoleEnum.ScrumMaster, new Image(RoleIconEnum.ScrumMaster.value.toString()));
    }};

    public static final HashMap<SubRoleEnum, Image> SUB_ROLE_ICON_MAPPING = new HashMap<SubRoleEnum, Image>() {{
        put(SubRoleEnum.WebDeveloper, new Image(RoleIconEnum.WebDeveloper.value.toString()));
        put(SubRoleEnum.FrontendDeveloper, new Image(RoleIconEnum.FrontendDeveloper.value.toString()));
        put(SubRoleEnum.BackendDeveloper, new Image(RoleIconEnum.BackendDeveloper.value.toString()));
        put(SubRoleEnum.FullStackDeveloper, new Image(RoleIconEnum.FullStackDeveloper.value.toString()));
        put(SubRoleEnum.MobileDeveloper, new Image(RoleIconEnum.MobileDeveloper.value.toString()));
        put(SubRoleEnum.SoftwareDeveloper, new Image(RoleIconEnum.SoftwareDeveloper.value.toString()));
        put(SubRoleEnum.GameDeveloper, new Image(RoleIconEnum.GameDeveloper.value.toString()));
        put(SubRoleEnum.SystemArchitect, new Image(RoleIconEnum.SystemArchitect.value.toString()));
        put(SubRoleEnum.SolutionArchitect, new Image(RoleIconEnum.SolutionArchitect.value.toString()));
        put(SubRoleEnum.SecurityArchitect, new Image(RoleIconEnum.SecurityArchitect.value.toString()));
        put(SubRoleEnum.DataArchitect, new Image(RoleIconEnum.DataArchitect.value.toString()));
        put(SubRoleEnum.TestAnalyst, new Image(RoleIconEnum.TestAnalyst.value.toString()));
        put(SubRoleEnum.AutomationTester, new Image(RoleIconEnum.AutomationTester.value.toString()));
        put(SubRoleEnum.ManualTester, new Image(RoleIconEnum.ManualTester.value.toString()));
        put(SubRoleEnum.PerformanceTester, new Image(RoleIconEnum.PerformanceTester.value.toString()));
    }};
}

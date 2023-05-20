package com.wise.resource.professionals.marketplace.constant;

import javafx.scene.image.Image;

import java.util.HashMap;

public class RoleIconMapping {
    public static final HashMap<MainRoleEnum, Image> MAIN_ROLE_ICON_MAPPING = new HashMap<MainRoleEnum, Image>() {{
        put(MainRoleEnum.DEVELOPER, new Image(RoleIconEnum.DEVELOPER.value.toString()));
        put(MainRoleEnum.DEV_OPS, new Image(RoleIconEnum.DEV_OPS.value.toString()));
        put(MainRoleEnum.ARCHITECT, new Image(RoleIconEnum.ARCHITECT.value.toString()));
        put(MainRoleEnum.UX_DESIGNER, new Image(RoleIconEnum.UX_DESIGNER.value.toString()));
        put(MainRoleEnum.TESTER, new Image(RoleIconEnum.TESTER.value.toString()));
        put(MainRoleEnum.BUSINESS_ANALYST, new Image(RoleIconEnum.BUSINESS_ANALYST.value.toString()));
        put(MainRoleEnum.SCRUM_MASTER, new Image(RoleIconEnum.SCRUM_MASTER.value.toString()));
    }};

    public static final HashMap<SubRoleEnum, Image> SUB_ROLE_ICON_MAPPING = new HashMap<SubRoleEnum, Image>() {{
        put(SubRoleEnum.WEB_DEVELOPER, new Image(RoleIconEnum.WEB_DEVELOPER.value.toString()));
        put(SubRoleEnum.FRONTEND_DEVELOPER, new Image(RoleIconEnum.FRONTEND_DEVELOPER.value.toString()));
        put(SubRoleEnum.BACKEND_DEVELOPER, new Image(RoleIconEnum.BACKEND_DEVELOPER.value.toString()));
        put(SubRoleEnum.FULL_STACK_DEVELOPER, new Image(RoleIconEnum.FULL_STACK_DEVELOPER.value.toString()));
        put(SubRoleEnum.MOBILE_DEVELOPER, new Image(RoleIconEnum.MOBILE_DEVELOPER.value.toString()));
        put(SubRoleEnum.SOFTWARE_DEVELOPER, new Image(RoleIconEnum.SOFTWARE_DEVELOPER.value.toString()));
        put(SubRoleEnum.GAME_DEVELOPER, new Image(RoleIconEnum.GAME_DEVELOPER.value.toString()));
        put(SubRoleEnum.SYSTEM_ARCHITECT, new Image(RoleIconEnum.SYSTEM_ARCHITECT.value.toString()));
        put(SubRoleEnum.SOLUTION_ARCHITECT, new Image(RoleIconEnum.SOLUTION_ARCHITECT.value.toString()));
        put(SubRoleEnum.SECURITY_ARCHITECT, new Image(RoleIconEnum.SECURITY_ARCHITECT.value.toString()));
        put(SubRoleEnum.DATA_ARCHITECT, new Image(RoleIconEnum.DATA_ARCHITECT.value.toString()));
        put(SubRoleEnum.TEST_ANALYST, new Image(RoleIconEnum.TEST_ANALYST.value.toString()));
        put(SubRoleEnum.AUTOMATION_TESTER, new Image(RoleIconEnum.AUTOMATION_TESTER.value.toString()));
        put(SubRoleEnum.MANUAL_TESTER, new Image(RoleIconEnum.MANUAL_TESTER.value.toString()));
        put(SubRoleEnum.PERFORMANCE_TESTER, new Image(RoleIconEnum.PERFORMANCE_TESTER.value.toString()));
    }};
}

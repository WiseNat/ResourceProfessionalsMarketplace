package com.wise.resource.professionals.marketplace.constant;

import java.util.HashMap;

public class RoleMapping {
    public static final HashMap<MainRoleEnum, SubRoleEnum[]> ROLE_MAPPING = new HashMap<MainRoleEnum, SubRoleEnum[]>() {{
        put(MainRoleEnum.DEVELOPER, new SubRoleEnum[]{
                SubRoleEnum.WEB_DEVELOPER,
                SubRoleEnum.FRONTEND_DEVELOPER,
                SubRoleEnum.BACKEND_DEVELOPER,
                SubRoleEnum.FULL_STACK_DEVELOPER,
                SubRoleEnum.MOBILE_DEVELOPER,
                SubRoleEnum.SOFTWARE_DEVELOPER,
                SubRoleEnum.GAME_DEVELOPER,
        });
        put(MainRoleEnum.DEV_OPS, new SubRoleEnum[0]);
        put(MainRoleEnum.ARCHITECT, new SubRoleEnum[]{
                SubRoleEnum.SYSTEM_ARCHITECT,
                SubRoleEnum.SOLUTION_ARCHITECT,
                SubRoleEnum.SECURITY_ARCHITECT,
                SubRoleEnum.DATA_ARCHITECT,
        });
        put(MainRoleEnum.UX_DESIGNER, new SubRoleEnum[0]);
        put(MainRoleEnum.TESTER, new SubRoleEnum[]{
                SubRoleEnum.TEST_ANALYST,
                SubRoleEnum.AUTOMATION_TESTER,
                SubRoleEnum.MANUAL_TESTER,
                SubRoleEnum.PERFORMANCE_TESTER,
        });
        put(MainRoleEnum.BUSINESS_ANALYST, new SubRoleEnum[0]);
        put(MainRoleEnum.SCRUM_MASTER, new SubRoleEnum[0]);
    }};
}

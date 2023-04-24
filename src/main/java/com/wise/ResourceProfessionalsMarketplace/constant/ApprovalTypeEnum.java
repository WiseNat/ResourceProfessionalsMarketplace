package com.wise.ResourceProfessionalsMarketplace.constant;

public enum ApprovalTypeEnum {
    CreateResourceAccount("Create Resource Account"),
    CreateProjectManagerAccount("Create Project Manager Account"),
    ;

    public final String value;

    ApprovalTypeEnum(String value) {
        this.value = value;
    }
}

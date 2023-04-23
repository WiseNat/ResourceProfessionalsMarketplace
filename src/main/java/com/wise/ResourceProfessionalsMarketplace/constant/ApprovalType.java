package com.wise.ResourceProfessionalsMarketplace.constant;

public enum ApprovalType {
    CreateResourceAccount("Create Resource Account"),
    CreateProjectManagerAccount("Create Project Manager Account"),
    ;

    public final String value;

    ApprovalType(String value) {
        this.value = value;
    }
}

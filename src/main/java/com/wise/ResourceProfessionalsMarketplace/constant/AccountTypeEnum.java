package com.wise.ResourceProfessionalsMarketplace.constant;

public enum AccountTypeEnum {
    Admin("Admin"),
    Resource("Resource"),
    ProjectManager("Project Manager"),
    ;

    public final String value;

    AccountTypeEnum(String value) {
        this.value = value;
    }
}

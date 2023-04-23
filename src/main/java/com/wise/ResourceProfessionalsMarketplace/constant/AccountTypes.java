package com.wise.ResourceProfessionalsMarketplace.constant;

public enum AccountTypes {
    Admin("Admin"),
    Resource("Resource"),
    ProjectManager("Project Manager"),
    ;

    public final String value;

    AccountTypes(String value) {
        this.value = value;
    }
}

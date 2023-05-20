package com.wise.resource.professionals.marketplace.to;

import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.validation.annotation.Roles;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Roles
@Data
public class RolesContainer {
    @NotNull
    private MainRoleEnum mainRole;

    private SubRoleEnum subRole;
}

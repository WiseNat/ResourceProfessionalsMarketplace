package com.wise.resource.professionals.marketplace.to;

import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.validation.annotation.Roles;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * A POJO used within other POJOs which defines both a main and sub role
 *
 * @see Roles
 * @see ResourceTO
 */
@Roles
@Data
public class RolesContainer {
    @NotNull
    private MainRoleEnum mainRole;

    private SubRoleEnum subRole;
}

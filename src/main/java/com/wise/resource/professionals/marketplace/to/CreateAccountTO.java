package com.wise.resource.professionals.marketplace.to;

import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A POJO for creating an account
 *
 * @see com.wise.resource.professionals.marketplace.controller.CreateAnAccountController#createAccount
 * @see com.wise.resource.professionals.marketplace.service.CreateAnAccountService#persistAccountAndApproval
 */
@Data
public class CreateAccountTO {

    private ResourceTO resource;

    @NotNull
    private AccountTypeEnum accountType;

    @Size(min = 1, max = 255)
    @NotNull
    private String firstName;

    @Size(min = 1, max = 255)
    @NotNull
    private String lastName;

    @Email
    @Size(min = 1, max = 255)
    @NotNull
    private String email;

    private String encodedPassword;

    @Size(min = 1)
    @NotNull
    private String password;

    private Boolean isApproved;
}

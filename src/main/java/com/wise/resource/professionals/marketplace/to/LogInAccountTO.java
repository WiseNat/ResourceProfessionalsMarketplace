package com.wise.resource.professionals.marketplace.to;

import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import lombok.Data;
import lombok.NonNull;

/**
 * A POJO for Logging in to an Account and for use in post authentication tasks
 *
 * @see com.wise.resource.professionals.marketplace.controller.LogInController#login
 * @see com.wise.resource.professionals.marketplace.controller.MainView#setAccountTO(LogInAccountTO)
 * @see com.wise.resource.professionals.marketplace.controller.ResourceController#accountEntity
 * @see com.wise.resource.professionals.marketplace.controller.ResourceController#resourceEntity
 */
@Data
public class LogInAccountTO {

    @NonNull
    private String email;

    @NonNull
    private String plaintextPassword;

    @NonNull
    private AccountTypeEnum accountType;
}

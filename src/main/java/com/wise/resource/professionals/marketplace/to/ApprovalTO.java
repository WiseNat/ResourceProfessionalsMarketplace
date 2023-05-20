package com.wise.resource.professionals.marketplace.to;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * A POJO for creating an account approval request
 *
 * @see CreateAccountTO
 * @see com.wise.resource.professionals.marketplace.service.CreateAnAccountService#persistAccountAndApproval(CreateAccountTO)
 * @see com.wise.resource.professionals.marketplace.service.CreateAnAccountService#persistApproval
 */
@Data
public class ApprovalTO {

    @NotNull
    private CreateAccountTO account;

    @NotNull
    private Date date;

}

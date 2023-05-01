package com.wise.resource.professionals.marketplace.to;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class ApprovalTO {

    @NotNull
    private CreateAccountTO account;

    @NotNull
    private Date date;

}
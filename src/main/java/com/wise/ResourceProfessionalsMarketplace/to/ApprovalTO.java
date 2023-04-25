package com.wise.ResourceProfessionalsMarketplace.to;

import com.wise.ResourceProfessionalsMarketplace.entity.AccountEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountTypeEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class ApprovalTO {

    @NotNull
    private AccountEntity account;

    @NotNull
    private Date date;

}

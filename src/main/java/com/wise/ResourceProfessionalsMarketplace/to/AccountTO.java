package com.wise.ResourceProfessionalsMarketplace.to;

import com.wise.ResourceProfessionalsMarketplace.entity.AccountTypeEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.ResourceEntity;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class AccountTO {


    private ResourceEntity resource;

//    @NotNull
    @Null
    private AccountTypeEntity accountType;

    @Size(min=1, max=255)
    @NotNull
    private String firstName;

    @Size(min=1, max=255)
    @NotNull
    private String lastName;

    @Email
    @Size(min=1, max=255)
    @NotNull
    private String email;

    @Size(min=60, max=60)
    @NotNull
    private String encodedPassword;

    @NotNull
    private Boolean is_approved;

    // TODO: Change to ResourceTO
    public void setResource(ResourceEntity resource) {
        this.resource = resource;
    }

    // TODO: Change to take AccountTypeEnum and pull from SQL
    public void setAccountType(AccountTypeEntity accountType) {
        this.accountType = accountType;
    }
}

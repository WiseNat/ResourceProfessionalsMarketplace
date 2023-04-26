package com.wise.ResourceProfessionalsMarketplace.to;

import com.wise.ResourceProfessionalsMarketplace.entity.AccountTypeEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.ResourceEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AccountTO {

    private ResourceEntity resource;

    @NotNull
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
    @Setter(AccessLevel.NONE)
    private String encodedPassword;

    @Size(min=1)
    @NotNull
    private String password;

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

    public void setPassword(String plaintextPassword, String encodedPassword) {
        this.password = plaintextPassword;
        this.encodedPassword = encodedPassword;
    }
}

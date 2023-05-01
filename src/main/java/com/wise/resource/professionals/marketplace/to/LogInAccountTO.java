package com.wise.resource.professionals.marketplace.to;

import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import lombok.Data;
import lombok.NonNull;

@Data
public class LogInAccountTO {

    @NonNull
    private String email;

    @NonNull
    private String plaintextPassword;

    @NonNull
    private AccountTypeEnum accountType;
}

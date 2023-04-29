package com.wise.ResourceProfessionalsMarketplace.to;

import com.wise.ResourceProfessionalsMarketplace.constant.AccountTypeEnum;
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

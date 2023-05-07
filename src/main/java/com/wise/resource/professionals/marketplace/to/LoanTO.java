package com.wise.resource.professionals.marketplace.to;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoanTO {

    @NotNull
    @NotEmpty
    private String clientName;

    @NotNull
    @Min(value = 1)
    private int amount;

}

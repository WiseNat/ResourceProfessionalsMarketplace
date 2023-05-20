package com.wise.resource.professionals.marketplace.to;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class LoanTO {

    @NotNull
    private ResourceCollectionTO resourceCollectionTO;

    @NotNull
    @NotEmpty
    private String clientName;

    @NotNull
    @Min(value = 1)
    private int amount;

    @NotNull
    @Future
    private Date availabilityDate;
}

package com.wise.resource.professionals.marketplace.to;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * A POJO for loaning anonymised resources
 *
 * @see com.wise.resource.professionals.marketplace.service.ProjectManagerService#createLoanTo
 * @see com.wise.resource.professionals.marketplace.service.ProjectManagerService#loanResource
 */
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

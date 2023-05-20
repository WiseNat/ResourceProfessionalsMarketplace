package com.wise.resource.professionals.marketplace.to;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

/**
 * A POJO for the raw user inputs when loaning a resource
 *
 * @see LoanTO
 * @see com.wise.resource.professionals.marketplace.controller.ProjectManagerController#loanButtonClicked
 * @see com.wise.resource.professionals.marketplace.service.ProjectManagerService#createLoanTo
 */
@Data
public class RawLoanTO {

    @NonNull
    private ResourceCollectionTO resourceCollectionTO;
    @NonNull
    private String clientName;

    @NonNull
    private int amount;

    @NonNull
    private LocalDate availabilityDate;

}

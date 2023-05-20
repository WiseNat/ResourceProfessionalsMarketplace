package com.wise.resource.professionals.marketplace.to;

import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * A POJO for a Return Search
 *
 * @see com.wise.resource.professionals.marketplace.module.LoanSearch#populatePredicateLoanables()
 * @see com.wise.resource.professionals.marketplace.service.LoanService#getLoanables(LoanSearchTO)
 */
@Data
public class LoanSearchTO {

    private BandingEnum banding;

    private SubRoleEnum subRole;

    private MainRoleEnum mainRole;

    @NotNull
    @Digits(integer = 5, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal costPerHour;
}

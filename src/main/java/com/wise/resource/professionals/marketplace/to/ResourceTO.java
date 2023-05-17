package com.wise.resource.professionals.marketplace.to;

import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceTO extends RolesContainer {

    @NotNull
    private BandingEnum banding;

    private String loanedClient;

    @NotNull
    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal dailyLateFee;

    @NotNull
    @Digits(integer = 5, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal costPerHour;

    private Date availabilityDate;
}

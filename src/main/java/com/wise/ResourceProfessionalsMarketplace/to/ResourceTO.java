package com.wise.ResourceProfessionalsMarketplace.to;

import com.wise.ResourceProfessionalsMarketplace.constant.BandingEnum;
import com.wise.ResourceProfessionalsMarketplace.constant.MainRoleEnum;
import com.wise.ResourceProfessionalsMarketplace.constant.SubRoleEnum;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;

@Data
public class ResourceTO {

    @NotNull
    private BandingEnum banding;

    private SubRoleEnum subRole;

    @NotNull
    private MainRoleEnum mainRole;

    private String loanedClient;

    @NotNull
    private Double dailyLateFee;

    @NotNull
    @Digits(integer = 5, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal costPerHour;

    private Date availabilityDate;
}

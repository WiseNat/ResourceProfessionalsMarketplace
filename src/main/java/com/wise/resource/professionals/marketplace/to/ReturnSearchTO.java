package com.wise.resource.professionals.marketplace.to;

import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import lombok.Data;

/**
 * A POJO for a Return Search
 *
 * @see com.wise.resource.professionals.marketplace.module.ReturnSearch#populatePredicateReturnables
 * @see com.wise.resource.professionals.marketplace.service.ReturnService#getReturnables
 */
@Data
public class ReturnSearchTO {

    private String firstName;

    private String lastName;

    private String client;

    private SubRoleEnum subRole;

    private MainRoleEnum mainRole;

    private BandingEnum banding;

}

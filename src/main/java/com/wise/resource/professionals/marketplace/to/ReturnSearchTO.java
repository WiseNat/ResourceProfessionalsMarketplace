package com.wise.resource.professionals.marketplace.to;

import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import lombok.Data;

@Data
public class ReturnSearchTO {

    private String firstName;

    private String lastName;

    private String client;

    private SubRoleEnum subRole;

    private MainRoleEnum mainRole;

    private BandingEnum banding;
}
